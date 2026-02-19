document.getElementById("Update")
.addEventListener("submit", function(event) {

    event.preventDefault(); 

    const dados = {
        nome: document.getElementById("nome").value || null,
        email: document.getElementById("email").value || null,
        senha: document.getElementById("senha").value || null,
        perfil: document.getElementById("perfil").value || null,
        cidade: document.getElementById("cidade").value || null,
    };

    fetch(`http://localhost:8080/usuarios/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(dados)
    })
    .then(response => response.json())
    .then(data => {
        alert("Usuário atualizado com sucesso!");
        console.log(data);
    })
    .catch(error => {
        console.error("Erro:", error);
    });
/* AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA */
});
