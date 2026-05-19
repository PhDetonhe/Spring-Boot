const AUTH_STORAGE_KEY = "petshop_auth";
const API_BASE_URL = "http://localhost:8080";

function getAuthData() {
    try {
        return JSON.parse(localStorage.getItem(AUTH_STORAGE_KEY)) || null;
    } catch {
        return null;
    }
}

function saveAuthData(data) {
    localStorage.setItem(AUTH_STORAGE_KEY, JSON.stringify(data));
}

function isLoggedIn() {
    return Boolean(getAuthData()?.token);
}

function isAdmin() {
    return getAuthData()?.role === "ADMIN";
}

function getAuthHeaders() {
    const token = getAuthData()?.token;
    return token ? { Authorization: `Bearer ${token}` } : {};
}

function logout() {
    localStorage.removeItem(AUTH_STORAGE_KEY);
    renderAuthState();
    refreshRoleDependentContent();
}

function openAuthModal(mode = "login") {
    const overlay = document.getElementById("authOverlay");
    if (!overlay) return;

    overlay.classList.remove("hidden");
    overlay.classList.add("flex");
    setAuthMode(mode);
}

function closeAuthModal() {
    const overlay = document.getElementById("authOverlay");
    if (!overlay) return;

    overlay.classList.add("hidden");
    overlay.classList.remove("flex");
}

function setAuthMode(mode) {
    const isRegister = mode === "register";
    document.getElementById("authNomeWrap")?.classList.toggle("hidden", !isRegister);
    document.getElementById("authSubmit")?.replaceChildren(document.createTextNode(isRegister ? "Criar conta" : "Entrar"));
    document.getElementById("authTitle")?.replaceChildren(document.createTextNode(isRegister ? "Criar conta" : "Entrar"));
    document.getElementById("authSubtitle")?.replaceChildren(document.createTextNode(isRegister ? "Cadastre-se como cliente para comprar produtos." : "Acesse sua conta para continuar."));
    document.getElementById("authMode")?.setAttribute("value", mode);
    document.getElementById("authMessage")?.replaceChildren();
}

function injectAuthModal() {
    if (document.getElementById("authOverlay")) return;

    document.body.insertAdjacentHTML("beforeend", `
        <div id="authOverlay" class="fixed inset-0 bg-charcoal/60 backdrop-blur-sm hidden items-center justify-center z-[300] px-4">
            <div class="bg-white rounded-2xl p-8 w-full max-w-md relative shadow-[0_30px_80px_rgba(0,0,0,0.25)] animate-slide-up">
                <button type="button" onclick="closeAuthModal()"
                    class="absolute top-5 right-5 w-8 h-8 rounded-full bg-cream hover:bg-border flex items-center justify-center text-sm transition-colors">
                    <i class="fa-solid fa-xmark"></i>
                </button>

                <h2 id="authTitle" class="font-display text-2xl font-bold text-charcoal mb-1">Entrar</h2>
                <p id="authSubtitle" class="text-muted text-sm mb-7">Acesse sua conta para continuar.</p>

                <form id="authForm" class="space-y-4">
                    <input id="authMode" type="hidden" value="login">
                    <div id="authNomeWrap" class="hidden">
                        <label class="block text-[11px] font-semibold tracking-widest uppercase text-charcoal mb-1.5">Nome</label>
                        <input id="authNome" type="text" autocomplete="name" placeholder="Seu nome"
                            class="w-full px-4 py-3 border-[1.5px] border-border rounded-xl bg-warm-white text-charcoal text-sm outline-none focus:border-teal-light focus:ring-2 focus:ring-teal-light/10 focus:bg-white transition-all placeholder-muted">
                    </div>
                    <div>
                        <label class="block text-[11px] font-semibold tracking-widest uppercase text-charcoal mb-1.5">E-mail</label>
                        <input id="authEmail" type="email" autocomplete="email" placeholder="voce@email.com"
                            class="w-full px-4 py-3 border-[1.5px] border-border rounded-xl bg-warm-white text-charcoal text-sm outline-none focus:border-teal-light focus:ring-2 focus:ring-teal-light/10 focus:bg-white transition-all placeholder-muted">
                    </div>
                    <div>
                        <label class="block text-[11px] font-semibold tracking-widest uppercase text-charcoal mb-1.5">Senha</label>
                        <input id="authSenha" type="password" autocomplete="current-password" placeholder="Sua senha"
                            class="w-full px-4 py-3 border-[1.5px] border-border rounded-xl bg-warm-white text-charcoal text-sm outline-none focus:border-teal-light focus:ring-2 focus:ring-teal-light/10 focus:bg-white transition-all placeholder-muted">
                    </div>
                    <button id="authSubmit" type="submit"
                        class="w-full bg-teal hover:bg-teal-dark text-white font-semibold py-3.5 rounded-xl transition-all hover:-translate-y-0.5">
                        Entrar
                    </button>
                </form>

                <div class="flex justify-between gap-3 mt-5 text-sm">
                    <button type="button" onclick="setAuthMode('login')" class="text-teal hover:text-teal-dark font-semibold">Ja tenho conta</button>
                    <button type="button" onclick="setAuthMode('register')" class="text-teal hover:text-teal-dark font-semibold">Criar cadastro</button>
                </div>
                <p id="authMessage" class="text-sm mt-4"></p>
            </div>
        </div>
    `);
}

function showAuthMessage(message, isError = true) {
    const el = document.getElementById("authMessage");
    if (!el) return;

    el.textContent = message;
    el.className = `text-sm mt-4 ${isError ? "text-red-500" : "text-teal"}`;
}

async function submitAuthForm(event) {
    event.preventDefault();

    const mode = document.getElementById("authMode")?.value || "login";
    const nome = document.getElementById("authNome")?.value.trim();
    const email = document.getElementById("authEmail")?.value.trim();
    const senha = document.getElementById("authSenha")?.value;

    if (!email || !senha || (mode === "register" && !nome)) {
        showAuthMessage("Preencha todos os campos obrigatorios.");
        return;
    }

    const endpoint = mode === "register" ? "/auth/cadastro" : "/auth/login";
    const body = mode === "register" ? { nome, email, senha } : { email, senha };

    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(body)
        });

        if (!response.ok) {
            const text = await response.text();
            throw new Error(text || "Nao foi possivel autenticar.");
        }

        const data = await response.json();
        saveAuthData(data);
        showAuthMessage("Login realizado com sucesso.", false);
        document.getElementById("authForm")?.reset();
        setTimeout(closeAuthModal, 400);
        renderAuthState();
        refreshRoleDependentContent();
    } catch (error) {
        showAuthMessage(error.message || "Erro ao conectar com o servidor.");
    }
}

function renderAuthState() {
    const auth = getAuthData();

    document.querySelectorAll("[data-auth-area]").forEach((area) => {
        if (auth?.token) {
            area.innerHTML = `
                <span class="hidden sm:inline text-sm font-semibold text-charcoal max-w-32 truncate">${auth.nome}</span>
                <button type="button" title="Sair" onclick="logout()"
                    class="w-10 h-10 rounded-full flex items-center justify-center hover:bg-cream hover:text-red-500 transition-all">
                    <i class="fa-solid fa-right-from-bracket"></i>
                </button>
            `;
        } else {
            area.innerHTML = `
                <button type="button" title="Entrar" onclick="openAuthModal('login')"
                    class="w-10 h-10 rounded-full flex items-center justify-center hover:bg-cream hover:text-teal transition-all">
                    <i class="fa-solid fa-user"></i>
                </button>
            `;
        }
    });

    document.querySelectorAll("[data-admin-only]").forEach((el) => {
        if (isAdmin()) {
            el.classList.remove("hidden");
            el.classList.add("flex");
        } else {
            el.classList.add("hidden");
            el.classList.remove("flex");
        }
    });
}

function refreshRoleDependentContent() {
    if (typeof carregarCategorias === "function") {
        carregarCategorias();
    }
}

function authErrorMessage(status) {
    if (status === 401) return "Voce precisa entrar novamente para fazer essa acao.";
    if (status === 403) return "Apenas administradores podem fazer essa acao.";
    return "Nao foi possivel completar a acao.";
}

document.addEventListener("DOMContentLoaded", () => {
    injectAuthModal();
    document.getElementById("authForm")?.addEventListener("submit", submitAuthForm);
    renderAuthState();
});

window.PetAuth = {
    getAuthData,
    isLoggedIn,
    isAdmin,
    getAuthHeaders,
    logout,
    openAuthModal,
    closeAuthModal,
    renderAuthState,
    refreshRoleDependentContent,
    authErrorMessage
};
