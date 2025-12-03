async function fazerLogin(event) {
    if (event) event.preventDefault();
    
    const login = document.getElementById('email').value;
    const senha = document.getElementById('password').value;
    
    if (!login || !senha) {
        alert('Por favor, preencha todos os campos.');
        return;
    }
    
    if (loginTeste(login, senha)) {
        alert('Login teste realizado com sucesso!');
        window.location.href = 'sucessful.html';
    } else {
        alert('E-mail ou senha incorretos para o login de teste.');
    }
}

function loginTeste(login, senha) {
    const credenciaisValidas = [
        { email: 'admin@gmail.com', senha: 'admin123' },
        { email: 'viniciuslindo@gmail.com', senha: 'viniciuslindo' }
    ];
    
    return credenciaisValidas.some(user => 
        user.email === login && user.senha === senha
    );
}