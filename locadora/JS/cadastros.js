document.getElementById('formCliente').addEventListener('submit', function (e) {
    e.preventDefault();
    const mensagem = document.getElementById('mensagemCliente');
    mensagem.textContent = 'Cliente cadastrado com sucesso.';
    mensagem.className = 'areaMensagem mensagemSucesso';
    mensagem.style.display = 'block';

    setTimeout(() => {
        mensagem.style.display = 'none';
        this.reset();
    }, 2000);
});

document.getElementById('formCarro').addEventListener('submit', function (e) {
    e.preventDefault();
    const mensagem = document.getElementById('mensagemCarro');
    mensagem.textContent = 'Carro cadastrado com sucesso.';
    mensagem.className = 'areaMensagem mensagemSucesso';
    mensagem.style.display = 'block';

    setTimeout(() => {
        mensagem.style.display = 'none';
        this.reset();
    }, 2000);
});
document.getElementById('formAgendamento').addEventListener('submit', function (e) {
    e.preventDefault();
    const mensagem = document.getElementById('mensagemAgendamento');
    mensagem.textContent = 'Agendamento realizado com sucesso.';
    mensagem.className = 'areaMensagem mensagemSucesso';
    mensagem.style.display = 'block';

    setTimeout(() => {
        mensagem.style.display = 'none';
        this.reset();
    }, 2000);
});
