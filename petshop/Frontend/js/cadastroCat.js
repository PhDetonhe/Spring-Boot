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
