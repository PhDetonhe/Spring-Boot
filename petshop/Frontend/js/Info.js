// ================== PEGAR ID DA URL ==================
const params = new URLSearchParams(window.location.search);
const id = params.get("id");

// ================== ELEMENTOS ==================
const nome = document.getElementById("nome");
const preco = document.getElementById("preco");
const descricao = document.getElementById("descricao");
const img = document.getElementById("img");
const categoria = document.getElementById("categoria");

// ================== VALIDAÇÃO ==================
if (!id) {
    document.body.innerHTML = "<p class='text-red-500'>Produto inválido</p>";
} else {

    // ================== BUSCAR PRODUTO ==================
    fetch(`http://localhost:8080/produtos/${id}`)
        .then(res => {
            if (!res.ok) throw new Error("Produto não encontrado");
            return res.json();
        })
        .then(produto => {

            // ================== PREENCHER HTML ==================
            nome.textContent = produto.nome;

            preco.textContent = "R$ " + Number(produto.preco).toFixed(2);

            descricao.textContent = produto.descricao || "Sem descrição";

            img.src = produto.imagem || "https://via.placeholder.com/300";

            categoria.textContent = produto.categoria?.nome || "Sem categoria";
        })
        .catch(err => {
            console.error(err);
            document.body.innerHTML = "<p class='text-red-500'>Erro ao carregar produto</p>";
        });
}

// ================== CONTROLE DE QUANTIDADE ==================
function qtd(valor) {
    const input = document.getElementById("q");
    let atual = parseInt(input.value) || 1;

    atual += valor;

    if (atual < 1) atual = 1;

    input.value = atual;
}