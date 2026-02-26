  document.getElementById("loginForm").addEventListener("submit", function (event) {
            event.preventDefault();

            const email = document.getElementById("email").value;
            const senha = document.getElementById("senha").value;
            const errorMsg = document.getElementById("errorMsg");

            errorMsg.textContent = "";

            fetch("http://localhost:8080/usuarios/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    email: email,
                    senha: senha
                })
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Email ou senha inválidos");
                    }
                    return response.json();
                })
                .then(usuario => {
                    // salva usuário no navegador
                    localStorage.setItem("usuarioLogado", JSON.stringify(usuario));

                    alert("Bem-vindo " + usuario.nome);

                    // redireciona
                    window.location.href = "users.html";
                })
                .catch(error => {
                    errorMsg.textContent = error.message;
                });
        });