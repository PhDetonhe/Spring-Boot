// FIX: todo o código movido para dentro do DOMContentLoaded
// Antes, getElementById era chamado no topo antes do DOM estar pronto,
// resultando em elementos null em alguns navegadores/situações de carregamento

let produtoAtual = null;

function quantidadeSelecionada() {
    return parseInt(document.getElementById("q").value) || 1;
}

function preencherProduto(produto) {
    produtoAtual = produto;
    document.getElementById("nome").textContent = produto.nome;
    document.getElementById("preco").textContent = "R$ " + Number(produto.preco_desconto || produto.preco).toFixed(2);
    document.getElementById("descricao").textContent = produto.descricao || "Sem descricao";
    document.getElementById("img").src = produto.imagem || "https://via.placeholder.com/300";
    document.getElementById("img").alt = produto.nome;
    document.getElementById("categoria").textContent = produto.categoria?.nome || "Sem categoria";
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
    const infoParams = new URLSearchParams(window.location.search);
    const id = infoParams.get("id");

    if (!id) {
        document.querySelector("main").innerHTML = "<p class='text-red-500'>Produto invalido</p>";
        return;
    }

    // Busca o produto após o DOM estar pronto
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