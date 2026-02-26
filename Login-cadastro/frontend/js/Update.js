// Pega o ID da URL (ex: update.html?id=2)
const urlParams = new URLSearchParams(window.location.search);
const userId = urlParams.get('usuario.id');

// Se não houver ID, redireciona para a listagem
if (!userId) {
    alert('ID de usuário não informado.');
    window.location.href = 'GetUsers.html';
}

// Elementos do formulário (IDs iguais aos do HTML)
const form = document.getElementById('Update');
const nomeInput = document.getElementById('nome');
const emailInput = document.getElementById('email');
const senhaInput = document.getElementById('senha');
const perfilInput = document.getElementById('perfil');
const enderecoInput = document.getElementById('endereco');
const bairroInput = document.getElementById('bairro');
const complementoInput = document.getElementById('complemento');
const cepInput = document.getElementById('cep');
const cidadeInput = document.getElementById('cidade');
const estadoInput = document.getElementById('estado');

// Função para carregar os dados do usuário e preencher o formulário
async function carregarUsuario() {
    try {
        const response = await fetch(`http://localhost:8080/usuarios/${userId}`);
        if (!response.ok) {
            throw new Error('Erro ao carregar usuário');
        }
        const usuario = await response.json();

        // Preenche os campos do formulário
        nomeInput.value = usuario.nome || '';
        emailInput.value = usuario.email || '';
        senhaInput.value = usuario.senha || ''; // cuidado: senha pode vir vazia
        perfilInput.value = usuario.perfil || '';
        enderecoInput.value = usuario.endereco || '';
        bairroInput.value = usuario.bairro || '';
        complementoInput.value = usuario.complemento || '';
        cepInput.value = usuario.cep || '';
        cidadeInput.value = usuario.cidade || '';
        estadoInput.value = usuario.estado || '';
    } catch (error) {
        console.error(error);
        alert('Não foi possível carregar os dados do usuário.');
        setTimeout(() => window.location.href = 'users.html', 3000);
    }
}

// Evento de submit do formulário
form.addEventListener('submit', async function (e) {
    e.preventDefault();

    // Monta o objeto com os dados atualizados
    const usuarioAtualizado = {
        nome: nomeInput.value,
        email: emailInput.value,
        senha: senhaInput.value,
        perfil: perfilInput.value,
        endereco: enderecoInput.value,
        bairro: bairroInput.value,
        complemento: complementoInput.value,
        cep: cepInput.value,
        cidade: cidadeInput.value,
        estado: estadoInput.value
    };

    try {
        const response = await fetch(`http://localhost:8080/usuarios/${userId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(usuarioAtualizado)
        });

        if (!response.ok) {
            // Tenta obter a mensagem de erro do corpo da resposta
            const errorText = await response.text();
            throw new Error(errorText || 'Erro na requisição');
        }

        // Se chegou aqui, deu certo
        alert('Usuário atualizado com sucesso!');
        window.location.href = 'GetUsers.html'; // redireciona para a listagem
    } catch (err) {
        console.error('Erro:', err);
        alert('Erro ao atualizar: ' + err.message);
    }
});

// Chama a função para carregar os dados assim que a página abrir
carregarUsuario();