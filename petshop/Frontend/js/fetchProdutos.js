// ================== LISTAR PRODUTOS ==================


const params = new URLSearchParams(window.location.search);
const categoriaId = params.get("categoria");


const nav = document.getElementById("sectionsprodutos");


if (!categoriaId) {
    nav.innerHTML = `<p class="text-red-500">Categoria inválida</p>`;
} else {

    
    fetch("http://localhost:8080/produtos")
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro na API: " + response.status);
            }
            return response.json();
        })
        .then(data => {

            nav.innerHTML = "";

            
            const filtrados = data.filter(p => 
                p.categoria && p.categoria.id_categoria == categoriaId
            );

            if (filtrados.length === 0) {
                nav.innerHTML = `<p class="text-gray-600">Nenhum produto encontrado</p>`;
                return;
            }

            filtrados.forEach(produto => {

                const item = `
                    <div class="bg-white p-4 rounded-2xl w-72 mx-5 my-6 border border-gray-200 
                    flex flex-col justify-between shadow-md hover:scale-105 transition">

                        <div class="flex justify-center">
                            <img src="${produto.imagem || "https://via.placeholder.com/150"}" 
                                 class="h-32 object-contain">
                        </div>

                        <h2 class="text-sm text-gray-800 mt-3 font-semibold">
                            ${produto.nome}
                        </h2>

                        <p class="text-gray-500 text-xs mt-2">
                            ${produto.descricao ?? "Sem descrição"}
                        </p>

                        <p class="text-lg font-bold text-black mt-3">
                            R$ ${Number(produto.preco).toFixed(2)}
                        </p>

                        <div class="flex justify-end mt-3">
           <a href="Infoprodutos.html?id=${produto.id_produto}">
        <button class="bg-green-500 p-3 rounded-full text-white hover:bg-green-600">
            🛒
        </button>
    </a>
</div>

                    </div>
                `;

                nav.innerHTML += item;
            });
        })
        .catch(error => {
            console.error("Erro:", error);
            nav.innerHTML = `<p class="text-red-500">Erro ao carregar produtos</p>`;
        });
}



// ================== CADASTRAR PRODUTO ==================

document.getElementById("registroForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const nome = document.getElementById("nome").value;
    const descricao = document.getElementById("descricao").value;
    const preco = parseFloat(document.getElementById("preco").value);
    const preco_desconto = parseFloat(document.getElementById("preco_desconto").value);
    const qtd_estoque = parseInt(document.getElementById("qtd_estoque").value);
    const idCategoria = document.getElementById("categoria").value;

    const erro = document.getElementById("erro");
    const resultado = document.getElementById("resultado");

    erro.textContent = "";
    resultado.textContent = "";

    
    if (!nome || !preco || !preco_desconto || !idCategoria) {
        erro.textContent = "Preencha os campos obrigatórios!";
        return;
    }

    const produto = {
        nome,
        descricao,
        preco,
        preco_desconto,
        qtd_estoque,
        imagem: ""
    };

    try {
        const response = await fetch(`http://localhost:8080/produtos/${idCategoria}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(produto)
        });

        if (!response.ok) {
            throw new Error("Erro ao cadastrar produto: " + response.status);
        }

        await response.json();

        resultado.textContent = "Produto cadastrado com sucesso!";

        document.getElementById("registroForm").reset();

        fecharProdutos();

        
        location.reload();

    } catch (err) {
        erro.textContent = err.message;
        console.error(err);
    }
});