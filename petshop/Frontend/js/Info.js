const infoParams = new URLSearchParams(window.location.search);
const id = infoParams.get("id");
let produtoAtual = null;

const nome = document.getElementById("nome");
const preco = document.getElementById("preco");
const descricao = document.getElementById("descricao");
const img = document.getElementById("img");
const categoria = document.getElementById("categoria");

function quantidadeSelecionada() {
    return parseInt(document.getElementById("q").value) || 1;
}

function preencherProduto(produto) {
    produtoAtual = produto;
    nome.textContent = produto.nome;
    preco.textContent = "R$ " + Number(produto.preco_desconto || produto.preco).toFixed(2);
    descricao.textContent = produto.descricao || "Sem descricao";
    img.src = produto.imagem || "https://via.placeholder.com/300";
    img.alt = produto.nome;
    categoria.textContent = produto.categoria?.nome || "Sem categoria";
}

if (!id) {
    document.querySelector("main").innerHTML = "<p class='text-red-500'>Produto invalido</p>";
} else {
    fetch(`http://localhost:8080/produtos/${id}`)
        .then(res => {
            if (!res.ok) throw new Error("Produto nao encontrado");
            return res.json();
        })
        .then(preencherProduto)
        .catch(err => {
            console.error(err);
            document.querySelector("main").innerHTML = "<p class='text-red-500'>Erro ao carregar produto</p>";
        });
}

function qtd(valor) {
    const input = document.getElementById("q");
    let atual = parseInt(input.value) || 1;
    atual += valor;
    if (atual < 1) atual = 1;
    if (produtoAtual?.qtd_estoque) atual = Math.min(atual, produtoAtual.qtd_estoque);
    input.value = atual;
}

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("addCartButton")?.addEventListener("click", () => {
        if (produtoAtual) PetCart.addItem(produtoAtual, quantidadeSelecionada());
    });

    document.getElementById("buyNowButton")?.addEventListener("click", () => {
        if (produtoAtual) {
            PetCart.addItem(produtoAtual, quantidadeSelecionada());
            PetCart.openCart();
        }
    });
});
