document.getElementById('Cadastro').addEventListener('submit', async function (e) {
    e.preventDefault();

    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;
    const perfil = document.getElementById('perfil').value;
    const endereco = document.getElementById('endereco').value;
    const bairro = document.getElementById('bairro').value;
    const complemento = document.getElementById('complemento').value;
    const cep = document.getElementById('cep').value;
    const cidade = document.getElementById('cidade').value;
    const estado = document.getElementById('estado').value;
    const fotoInput = document.getElementById('fotoInput');
    const preview = document.getElementById('preview');

    let resultado = 0;
    let erro = 0;


    try {
        const response = await fetch('http://localhost:8080/user', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                nome: name,
                email: email,
                senha: senha,
                perfil: perfil,
                endereco: endereco,
                bairro: bairro,
                complemento: complemento,
                cep: cep,
                cidade: cidade,
                estado: estado,
                foto: fotoBase64

            })
        });

        if (!response.ok) {
            throw new Error('Erro na requisição');
        }

        const data = await response.json();

        if (data.erro) {
            document.getElementById('erro').textContent = data.erro;
        } else {
            document.getElementById('resultado').textContent = 'Usuário cadastrado com sucesso!';
        }

    } catch (err) {
        document.getElementById('erro').textContent = 'Erro ao processar a requisição' + err.message;
    }
});

 const fotoInput = document.getElementById('fotoInput');
    const preview = document.getElementById('preview');
    let fotoBase64 = ""; // Variável global para guardar a string da foto

    // Evento para ler a foto assim que o usuário seleciona o arquivo
    fotoInput.addEventListener('change', function () {
        const arquivo = this.files[0];
        if (arquivo) {
            const reader = new FileReader();
            reader.onload = function (e) {
                fotoBase64 = e.target.result; // Aqui está o texto da imagem
                preview.src = fotoBase64;
                preview.style.display = 'block';
            };
            reader.readAsDataURL(arquivo);
        }
    });