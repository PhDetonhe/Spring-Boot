const form = document.getElementById('Cadastro');
const mensagem = document.getElementById('mensagem');

form.addEventListener('submit', function(e) {
    e.preventDefault();

    const usuario = {
        nome: document.getElementById('nome').value.trim(),
        email: document.getElementById('email').value.trim(),
        senha: document.getElementById('senha').value,
        perfil: document.getElementById('perfil').value,
        endereco: document.getElementById('endereco').value.trim(),
        bairro: document.getElementById('bairro').value.trim(),
        complemento: document.getElementById('complemento').value.trim(),
        cep: document.getElementById('cep').value.trim(),
        cidade: document.getElementById('cidade').value.trim(),
        estado: document.getElementById('estado').value.trim()
    };

    fetch('http://localhost:8080/usuarios', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(usuario)
    })
    .then(res => {
        if (!res.ok) throw new Error();
        mensagem.innerHTML = '<span class="sucesso">Usuário cadastrado com sucesso!</span>';
        form.reset();
    })
    .catch(() => {
        mensagem.innerHTML = '<span class="erro">Erro ao cadastrar usuário.</span>';
    });
});