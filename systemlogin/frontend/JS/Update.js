document.getElementById("Update")
.addEventListener("submit", function(event) {

    event.preventDefault(); 

    
    const id = document.getElementById("usuarioId")?.value || 1; 

    const dados = {
        id: "id",
        email: document.getElementById("email")?.value || null,
        senha: document.getElementById("senha")?.value || null,  
    };

    
    if (!dados.email && !dados.senha) {
        alert("Pelo menos um campo deve ser preenchido!");
        return;
    }

    fetch(`http://localhost:8080/usuarios/${id}`, {
        method: "PUT",
        headers: {
        "Content-Type": "application/x-www-form-urlencoded",
         "Accept": "application/json, application/xml, text/plain, */*",
        "X-Requested-With": "XMLHttpRequest"
    },
        body: JSON.stringify(dados)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        alert("Usuário atualizado com sucesso!");
        console.log("Resposta:", data);
    })
    .catch(error => {
        console.error("Erro detalhado:", error);
        alert("Erro ao atualizar usuário. Verifique o console.");
    });

});