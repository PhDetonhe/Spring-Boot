fetch("http://localhost:8080/user")
    .then(response => response.json())
    .then(data => {
        const tabela = document.getElementById("tabelaUsuarios");

        data.forEach(usuario => {
            const linha = `
                <tr>
                    <td><img src="${usuario.foto}" class="fotoUsuario"></td>
                    <td>${usuario.name}</td>
                    <td>${usuario.email}</td>
                    <td>${usuario.senha}</td>
                    <td>${usuario.perfil}</td>
                    <td>${usuario.cidade}</td>
                    <td>
                    <a href="Update.html?id=${usuario.id}"><button type = "submit" class = "NEWUSER" onclick = "editar(${usuario.id})">Editar</button></a>
                    <button type = "submit" class = "NEWUSER" onclick = "deletar(${usuario.id})">Excluir</button>
                    </td>
                </tr>
            `;
            tabela.innerHTML += linha;
        });
    })
    .catch(error => console.error("Erro:", error));