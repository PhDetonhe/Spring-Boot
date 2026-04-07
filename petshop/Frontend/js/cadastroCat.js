document.getElementById('registroForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    function redirecionarUsuarios() {
        window.location.href = "main.html";
    }

    const nome = document.getElementById('nome').value;
    const descricao = document.getElementById('descricao').value;

    try {
        const response = await fetch('http://localhost:8080/categorias', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                nome: nome,
                descricao: descricao,
                ativo: true 
            })
        });

        if (!response.ok) {
            throw new Error('Erro na requisição: ' + response.status);
        }

        const data = await response.json();

        console.log("Resposta do backend:", data); 

        
        document.getElementById('resultado').textContent = 'Categoria cadastrada com sucesso!';
        document.getElementById('erro').textContent = '';

        setTimeout(redirecionarUsuarios, 1000);

    } catch (err) {
        console.error(err); 

        document.getElementById('erro').textContent = 'Erro: ' + err.message;
        document.getElementById('resultado').textContent = '';
    }
});





