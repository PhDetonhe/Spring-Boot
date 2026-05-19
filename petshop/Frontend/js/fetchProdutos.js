const params = new URLSearchParams(window.location.search);
const categoriaId = params.get("categoria");
const produtosContainer = document.getElementById("sectionsprodutos");
window.petshopProducts = window.petshopProducts || {};

function renderProdutos(produtos) {
    if (!produtosContainer) return;

    produtosContainer.innerHTML = "";

    if (produtos.length === 0) {
        produtosContainer.innerHTML = `<p class="text-gray-600">Nenhum produto encontrado</p>`;
        return;
    }

    produtos.forEach((produto) => {
        window.petshopProducts[produto.id_produto] = produto;
        const preco = Number(produto.preco_desconto || produto.preco).toFixed(2);

        produtosContainer.innerHTML += `
            <div class="bg-white p-4 rounded-xl border border-border flex flex-col justify-between shadow-sm hover:scale-[1.03] hover:shadow-xl transition">
                <a href="Infoprodutos.html?id=${produto.id_produto}" class="block">
                    <div class="flex justify-center bg-cream rounded-lg">
                        <img src="${produto.imagem || "https://via.placeholder.com/150"}" alt="${produto.nome}" class="h-32 object-contain p-3">
                    </div>
                    <h2 class="text-sm text-charcoal mt-3 font-semibold">${produto.nome}</h2>
                    <p class="text-muted text-xs mt-2 line-clamp-2">${produto.descricao || "Sem descricao"}</p>
                    <p class="text-lg font-bold text-charcoal mt-3">R$ ${preco}</p>
                </a>
                <div class="flex justify-end mt-3">
                    <button type="button" onclick="PetCart.addItem(window.petshopProducts[${produto.id_produto}])"
                        class="bg-teal p-3 rounded-full text-white hover:bg-teal-dark transition-colors" title="Adicionar ao carrinho">
                        <i class="fa-solid fa-cart-shopping"></i>
                    </button>
                </div>
            </div>
        `;
    });
}

function carregarProdutos() {
    if (!produtosContainer) return;

    if (!categoriaId) {
        produtosContainer.innerHTML = `<p class="text-red-500">Categoria invalida</p>`;
        return;
    }

    fetch("http://localhost:8080/produtos")
        .then(response => {
            if (!response.ok) throw new Error("Erro na API: " + response.status);
            return response.json();
        })
        .then(data => {
            const filtrados = data.filter(produto =>
                produto.categoria && produto.categoria.id_categoria == categoriaId
            );
            renderProdutos(filtrados);
        })
        .catch(error => {
            console.error("Erro:", error);
            produtosContainer.innerHTML = `<p class="text-red-500">Erro ao carregar produtos</p>`;
        });
}

document.addEventListener("DOMContentLoaded", carregarProdutos);

document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("produtoForm");
    if (!form) return;

    form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const nome = document.getElementById("produtoNome").value.trim();
        const descricao = document.getElementById("produtoDescricao").value.trim();
        const preco = parseFloat(document.getElementById("produtoPreco").value);
        const preco_desconto = parseFloat(document.getElementById("produtoPrecoDesconto").value);
        const qtd_estoque = parseInt(document.getElementById("produtoEstoque").value);
        const idCategoria = document.getElementById("produtoCategoria").value;
        const erro = document.getElementById("produtoErro");
        const resultado = document.getElementById("produtoResultado");

        erro.textContent = "";
        resultado.textContent = "";

        if (!PetAuth.isAdmin()) {
            erro.textContent = "Apenas administradores podem cadastrar produtos.";
            return;
        }

        if (!nome || Number.isNaN(preco) || Number.isNaN(preco_desconto) || !idCategoria) {
            erro.textContent = "Preencha os campos obrigatorios.";
            return;
        }

        const produto = {
            nome,
            descricao,
            preco,
            preco_desconto,
            qtd_estoque: Number.isNaN(qtd_estoque) ? 0 : qtd_estoque,
            imagem: ""
        };

        try {
            const response = await fetch(`http://localhost:8080/produtos/${idCategoria}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    ...PetAuth.getAuthHeaders()
                },
                body: JSON.stringify(produto)
            });

            if (!response.ok) {
                throw new Error(PetAuth.authErrorMessage(response.status));
            }

            resultado.textContent = "Produto cadastrado com sucesso!";
            form.reset();
            fecharProdutos();
            carregarProdutos();
        } catch (err) {
            erro.textContent = err.message || "Erro ao cadastrar produto.";
        }
    });
});
