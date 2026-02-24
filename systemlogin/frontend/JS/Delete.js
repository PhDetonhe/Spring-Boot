async function deletar(userId) {
    if (!confirm('Tem certeza que deseja excluir este usuário?')) {
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/usuarios/${userId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const row = document.querySelector(`button[onclick="deletar(${userId})"]`)
            .closest('tr');
            row.remove();
            
            alert('Usuário excluído com sucesso!');
        } else {
            const error = await response.text();
            alert(`Erro ao excluir usuário: ${error}`);
        }
    } catch (error) {
        console.error('Erro:', error);
        alert('Erro ao conectar com o servidor');
    }
}