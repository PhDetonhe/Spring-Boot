function showAdminMessage(message, isError = true) {
    const el = document.getElementById("adminMessage");
    if (!el) return;

    el.textContent = message;
    el.className = `text-sm mt-4 ${isError ? "text-red-500" : "text-teal"}`;
}

function renderAdminPageState() {
    const isAdmin = PetAuth.isAdmin();

    document.getElementById("adminGuard")?.classList.toggle("hidden", isAdmin);
    document.getElementById("adminContent")?.classList.toggle("hidden", !isAdmin);

    if (isAdmin) {
        carregarUsuariosAdmin();
    }
}

async function carregarUsuariosAdmin() {
    if (!PetAuth.isAdmin()) return;

    const tbody = document.getElementById("adminUsersTable");
    if (!tbody) return;

    tbody.innerHTML = `<tr><td colspan="3" class="py-4 text-muted">Carregando...</td></tr>`;

    try {
        const response = await fetch("http://localhost:8080/usuarios", {
            headers: PetAuth.getAuthHeaders()
        });

        if (!response.ok) {
            throw new Error(PetAuth.authErrorMessage(response.status));
        }

        const usuarios = await response.json();
        if (usuarios.length === 0) {
            tbody.innerHTML = `<tr><td colspan="3" class="py-4 text-muted">Nenhum usuario cadastrado.</td></tr>`;
            return;
        }

        tbody.innerHTML = usuarios.map((usuario) => `
            <tr>
                <td class="py-3 pr-4 font-semibold">${usuario.nome || "-"}</td>
                <td class="py-3 pr-4 text-muted">${usuario.email || "-"}</td>
                <td class="py-3">
                    <span class="inline-flex items-center px-2.5 py-1 rounded-full text-xs font-bold ${usuario.role === "ADMIN" ? "bg-amber/30 text-charcoal" : "bg-cream text-muted"}">
                        ${usuario.role}
                    </span>
                </td>
                <td class="py-3">
                    <button onclick="deleteUsuario(${usuario.id})" 
                        class="text-red-500 hover:text-red-700 font-semibold text-sm transition-colors">
                        Deletar
                    </button>
                </td>
            </tr>
        `).join("");
    } catch (error) {
        tbody.innerHTML = `<tr><td colspan="3" class="py-4 text-red-500">${error.message || "Erro ao carregar usuarios."}</td></tr>`;
    }
}

async function cadastrarAdmin(event) {
    event.preventDefault();

    const nome = document.getElementById("adminNome")?.value.trim();
    const email = document.getElementById("adminEmail")?.value.trim();
    const senha = document.getElementById("adminSenha")?.value;

    showAdminMessage("");

    if (!nome || !email || !senha) {
        showAdminMessage("Preencha todos os campos.");
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/auth/cadastro/admin", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                ...PetAuth.getAuthHeaders()
            },
            body: JSON.stringify({ nome, email, senha })
        });

        if (!response.ok) {
            const text = await response.text();
            throw new Error(text || PetAuth.authErrorMessage(response.status));
        }

        document.getElementById("adminForm")?.reset();
        showAdminMessage("Administrador criado com sucesso.", false);
        carregarUsuariosAdmin();
    } catch (error) {
        showAdminMessage(error.message || "Erro ao criar administrador.");
    }
}

// Deletar usuário
async function deleteUsuario(usuarioId) {
    if (!confirm("Tem certeza que deseja deletar este usuário?")) {
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/usuarios/${usuarioId}`, {
            method: "DELETE",
            headers: PetAuth.getAuthHeaders()
        });

        if (!response.ok) {
            const text = await response.text();
            throw new Error(text || "Erro ao deletar usuário");
        }

        showAdminMessage("Usuário deletado com sucesso.", false);
        carregarUsuariosAdmin();
    } catch (error) {
        showAdminMessage(error.message || "Erro ao deletar usuário.");
    }
}

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("adminForm")?.addEventListener("submit", cadastrarAdmin);
    renderAdminPageState();
});

window.carregarUsuariosAdmin = carregarUsuariosAdmin;
