fetch("http://localhost:8080/usuarios")
    .then(response => response.json())
    .then(data => {
        const tabela = document.getElementById("tabelaUsuarios");

        data.forEach(usuario => {
            const linha = `
                <tr>
                    <td>${usuario.nome}</td>
                    <td>${usuario.email}</td>
                    <td>${usuario.senha}</td>
                    <td>${usuario.perfil}</td>
                    <td>${usuario.cidade}</td>
                    <td>
                    <a href="Update.html"><button type = "submit" class = "editar" onclick = "editar(${usuario.id})">Editar</button></a>
                    <button type = "submit" class = "deletar" onclick = "deletar(${usuario.id})">Excluir</button>
                    </td>
                </tr>
            `;
            tabela.innerHTML += linha;
        });
    })
    .catch(error => console.error("Erro:", error));