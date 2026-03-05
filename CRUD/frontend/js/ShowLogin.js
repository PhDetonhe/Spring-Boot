 function mostrarUsuarioLogado() {
            const userInfo = document.getElementById('userInfo');
            const usuarioLogado = localStorage.getItem('usuarioLogado');
            
            if (usuarioLogado) {
                const usuario = JSON.parse(usuarioLogado);
                userInfo.innerHTML = `
                    <span class="user-name">👤 ${usuario.nome}</span>
                    <a href="login.html" class="logout-btn" onclick="logout()">Sair</a>
                `;
            } else {
                // Se não estiver logado, redireciona para o login
                window.location.href = 'login.html';
            }
        }

        // Função de logout
        function logout() {
            localStorage.removeItem('usuarioLogado');
            window.location.href = 'login.html';
        }

        // Chama a função quando a página carregar
        mostrarUsuarioLogado();