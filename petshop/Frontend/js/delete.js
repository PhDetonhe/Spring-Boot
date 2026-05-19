async function excluir(id_categoria) {
    if (!PetAuth.isAdmin()) {
        alert("Apenas administradores podem excluir categorias.");
        return;
    }

    if (!confirm("Tem certeza que deseja excluir esta categoria?")) {
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/categorias/${id_categoria}`, {
            method: "DELETE",
            headers: PetAuth.getAuthHeaders()
        });

        if (!response.ok) {
            throw new Error(PetAuth.authErrorMessage(response.status));
        }

        alert("Categoria excluida com sucesso!");
        carregarCategorias();
    } catch (error) {
        alert(error.message || "Erro ao conectar com o servidor.");
    }
}
