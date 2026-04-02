// PRODUTOS---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // 🔹 Pega o ID da categoria da URL
const params = new URLSearchParams(window.location.search);
const categoriaId = params.get("categoria");

// 🔹 Seleciona o container
const nav = document.getElementById("sectionsprodutos");

// 🔹 Validação simples
if (!categoriaId) {
    nav.innerHTML = `<p class="text-red-500">Categoria inválida</p>`;
    throw new Error("Categoria não informada");
}

// 🔹 Fetch filtrando por categoria
fetch(`http://localhost:8080/produtos/categoria/${categoriaId}`)
    .then(response => response.json())
    .then(data => {

        nav.innerHTML = ""; 

        data.forEach(produto => {
            const item = `
                <div class="bg-white p-4 rounded-2xl w-72 mx-5 my-6 border border-gray-200 
                flex flex-col justify-between shadow-md hover:scale-105 transition">

                    <!-- IMAGEM -->
                    <div class="flex justify-center">
                        <img src="${produto.imagem}" alt="${produto.nome}" class="h-32 object-contain">
                    </div>

                    <!-- NOME -->
                    <h2 class="text-sm text-gray-800 mt-3">
                        ${produto.nome}
                    </h2>

                    <!-- PREÇO NORMAL -->
                    <p class="text-gray-500 text-sm mt-2">
                        À vista por
                    </p>

                    <p class="text-lg font-bold text-black">
                        R$ ${produto.preco.toFixed(2)}
                    </p>

                    <!-- PREÇO PROMO -->
                    <div class="mt-2">
                        <span class="text-orange-500 font-semibold">
                            Compra Programada
                        </span>
                        <p class="text-orange-600 font-bold">
                            R$ ${produto.preco_desconto.toFixed(2)}
                            <span class="text-xs bg-orange-200 px-1 rounded">
                                -10%
                            </span>
                        </p>
                    </div>

                    <!-- BOTÃO -->
                    <div class="flex justify-end mt-3">
                        <button class="bg-green-500 p-3 rounded-full text-white hover:bg-green-600">
                            🛒
                        </button>
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