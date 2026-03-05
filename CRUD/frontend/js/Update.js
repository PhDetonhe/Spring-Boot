const params = new URLSearchParams(window.location.search);
const userId = params.get("id");

if (!userId) {
    alert("ID não informado");
    window.location.href = "GetUsers.html";
}

const form = document.getElementById("Update");

// Carregar dados
async function carregarUsuario() {
    try {
        const response = await fetch(`http://localhost:8080/user/${userId}`);
        const usuario = await response.json();

        // Preenche automaticamente todos os inputs que tenham name igual ao atributo do JSON
        Object.keys(usuario).forEach(key => {
            const campo = document.querySelector(`[name="${key}"]`);
            if (campo) campo.value = usuario[key] || "";
        });

    } catch (error) {
        alert("Erro ao carregar usuário");
        window.location.href = "GetUsers.html";
    }
}

// Atualizar
form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const formData = new FormData(form);
    const usuarioAtualizado = Object.fromEntries(formData.entries());

    try {
        await fetch(`http://localhost:8080/user/${userId}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(usuarioAtualizado)
        });

        alert("Atualizado com sucesso!");
        window.location.href = "GetUsers.html";

    } catch (error) {
        alert("Erro ao atualizar");
    }
});

carregarUsuario();