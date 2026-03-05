document.getElementById("loginForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const email = document.getElementById("email").value;
    const senha = document.getElementById("senha").value;

    const params = new URLSearchParams();
    params.append("username", email);  // Spring espera "username"
    params.append("password", senha);  // Spring espera "password"

    fetch("/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: params,
        credentials: "include"
    })
    .then(response => {
        if (response.redirected) {
            window.location.href = response.url;
        } else {
            document.getElementById("errorMsg").innerText = "Email ou senha inválidos.";
        }
    })
    .catch(() => {
        document.getElementById("errorMsg").innerText = "Erro ao tentar logar.";
    });
});