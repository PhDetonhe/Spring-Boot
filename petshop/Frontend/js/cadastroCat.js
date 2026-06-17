document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("categoriaForm");
    if (!form) return;

    form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const nome = document.getElementById("categoriaNome").value.trim();
        const descricao = document.getElementById("categoriaDescricao").value.trim();
        const erro = document.getElementById("categoriaErro");
        const resultado = document.getElementById("categoriaResultado");

        erro.textContent = "";
        resultado.textContent = "";

        if (!PetAuth.isAdmin()) {
            erro.textContent = "Apenas administradores podem cadastrar categorias.";
            return;
        }

        if (!nome) {
            erro.textContent = "Preencha o nome da categoria.";
            return;
        }

        try {
            const response = await fetch("http://localhost:8080/categorias", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    ...PetAuth.getAuthHeaders()
                },
                body: JSON.stringify({ nome, descricao, ativo: true })
            });

            if (!response.ok) {
                throw new Error(PetAuth.authErrorMessage(response.status));
            }

            resultado.textContent = "Categoria cadastrada com sucesso!";
            form.reset();
            fechar();
            carregarCategorias();
        } catch (err) {
            erro.textContent = err.message || "Erro ao cadastrar categoria.";
        }
    });
});

// Editar categoria
async function editarCategoria(idCategoria) {
    const nome = prompt("Novo nome da categoria:");
    if (!nome || nome.trim() === "") return;

    const descricao = prompt("Nova descrição da categoria:");

    try {
        const response = await fetch(`http://localhost:8080/categorias/${idCategoria}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                ...PetAuth.getAuthHeaders()
            },
            body: JSON.stringify({ nome, descricao, ativo: true })
        });

        if (!response.ok) {
            throw new Error("Erro ao editar categoria");
        }

        alert("Categoria atualizada com sucesso!");
        carregarCategorias();
    } catch (error) {
        alert("Erro: " + error.message);
    }
}

// Deletar categoria
async function deletarCategoria(idCategoria) {
    if (!confirm("Tem certeza que deseja deletar esta categoria?")) {
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/categorias/${idCategoria}`, {
            method: "DELETE",
            headers: PetAuth.getAuthHeaders()
        });

        if (!response.ok) {
            throw new Error("Erro ao deletar categoria");
        }

        alert("Categoria deletada com sucesso!");
        carregarCategorias();
    } catch (error) {
        alert("Erro: " + error.message);
    }
}
