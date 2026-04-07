async function excluir(id_categoria, botao) {
    if (!confirm('Tem certeza que deseja excluir esta categoria?')) {
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/categorias/${id_categoria}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            const card = botao.closest('div');
            card.remove();

            alert('Categoria excluída com sucesso!');
        } else {
            const error = await response.text();
            alert(`Erro ao excluir categoria: ${error}`);
        }
    } catch (error) {
        console.error('Erro:', error);
        alert('Erro ao conectar com o servidor');
    }
}