const CART_STORAGE_KEY = "petshop_cart";
const CART_API_BASE_URL = "http://localhost:8080";

let currentPedido = null;
let currentBackendItems = [];

function getCart() {
    try {
        return JSON.parse(localStorage.getItem(CART_STORAGE_KEY)) || [];
    } catch {
        return [];
    }
}

function saveCart(items) {
    localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(items));
    renderCartState();
}

function getLoggedUserId() {
    return PetAuth.getAuthData()?.id || null;
}

function usesBackendCart() {
    return PetAuth.isLoggedIn() && Boolean(getLoggedUserId());
}

function getPedidoHeaders() {
    return {
        "Content-Type": "application/json",
        ...PetAuth.getAuthHeaders()
    };
}

async function fetchPedidoJson(path, options = {}) {
    const response = await fetch(`${CART_API_BASE_URL}${path}`, {
        ...options,
        headers: {
            ...getPedidoHeaders(),
            ...(options.headers || {})
        }
    });

    if (!response.ok) {
        const text = await response.text();
        throw new Error(text || "Nao foi possivel atualizar o carrinho.");
    }

    const contentType = response.headers.get("content-type") || "";
    return contentType.includes("application/json") ? response.json() : response.text();
}

async function loadBackendCart() {
    const usuarioId = getLoggedUserId();
    if (!usuarioId) return { pedido: null, itens: [] };

    try {
        const pedido = await fetchPedidoJson(`/pedidos/carrinho/${usuarioId}`);
        const itens = await fetchPedidoJson(`/pedidos/${pedido.idPedido}/itens`);
        currentPedido = pedido;
        currentBackendItems = itens;
        return { pedido, itens };
    } catch (error) {
        currentPedido = null;
        currentBackendItems = [];
        return { pedido: null, itens: [] };
    }
}

function cartItemFromProduct(product, qtd = 1) {
    const price = Number(product.preco_desconto || product.preco || 0);

    return {
        id_produto: product.id_produto,
        nome: product.nome,
        preco: price,
        imagem: PetImages.productImageSrc(product.imagem),
        qtd,
        qtd_estoque: product.qtd_estoque ?? null
    };
}

async function addItem(product, qtd = 1) {
    if (!product?.id_produto) return;

    if (usesBackendCart()) {
        try {
            await fetchPedidoJson(`/pedidos/carrinho/adicionar?usuarioId=${getLoggedUserId()}&idProduto=${product.id_produto}&quantidade=${qtd}`, {
                method: "POST"
            });
            await openCart();
        } catch (error) {
            alert(error.message || "Erro ao adicionar item ao carrinho.");
        }
        return;
    }

    const items = getCart();
    const existing = items.find((item) => item.id_produto === product.id_produto);
    const stock = product.qtd_estoque ?? existing?.qtd_estoque ?? null;

    if (existing) {
        existing.qtd += qtd;
        if (stock !== null) existing.qtd = Math.min(existing.qtd, stock);
    } else {
        items.push(cartItemFromProduct(product, qtd));
    }

    saveCart(items);
    openCart();
}

async function setQuantity(id, qtd) {
    if (usesBackendCart()) {
        if (!currentPedido?.idPedido) return;

        try {
            await fetchPedidoJson(`/pedidos/itens/${id}?idPedido=${currentPedido.idPedido}&quantidade=${Math.max(0, Number(qtd) || 0)}`, {
                method: "PUT"
            });
            await renderCartState();
        } catch (error) {
            alert(error.message || "Erro ao atualizar quantidade.");
        }
        return;
    }

    const items = getCart();
    const item = items.find((cartItem) => cartItem.id_produto === id);
    if (!item) return;

    const nextQtd = Math.max(1, Number(qtd) || 1);
    item.qtd = item.qtd_estoque !== null ? Math.min(nextQtd, item.qtd_estoque) : nextQtd;
    saveCart(items);
}

async function removeItem(id) {
    if (usesBackendCart()) {
        if (!currentPedido?.idPedido) return;

        try {
            await fetchPedidoJson(`/pedidos/carrinho/remover/${id}?idPedido=${currentPedido.idPedido}`, {
                method: "DELETE"
            });
            await renderCartState();
        } catch (error) {
            alert(error.message || "Erro ao remover item.");
        }
        return;
    }

    saveCart(getCart().filter((item) => item.id_produto !== id));
}

async function clearCart() {
    if (usesBackendCart()) {
        await Promise.all(currentBackendItems.map((item) => removeItem(item.idItemPedido)));
        await renderCartState();
        return;
    }

    saveCart([]);
}

async function checkoutCart() {
    if (!usesBackendCart()) {
        PetAuth.openAuthModal("login");
        return;
    }

    if (!currentPedido?.idPedido || currentBackendItems.length === 0) {
        alert("Seu carrinho esta vazio.");
        return;
    }

    try {
        const message = await fetchPedidoJson(`/pedidos/finalizar/${currentPedido.idPedido}`, {
            method: "PUT"
        });
        
        // Redirecionar para página de confirmação
        window.location.href = `checkout.html?pedidoId=${currentPedido.idPedido}`;
    } catch (error) {
        alert(error.message || "Erro ao finalizar pedido.");
    }
}

async function syncLocalCartToBackend() {
    if (!usesBackendCart()) return;

    const localItems = getCart();
    if (localItems.length === 0) {
        await renderCartState();
        return;
    }

    try {
        await Promise.all(localItems.map((item) =>
            fetchPedidoJson(`/pedidos/carrinho/adicionar?usuarioId=${getLoggedUserId()}&idProduto=${item.id_produto}&quantidade=${item.qtd}`, {
                method: "POST"
            })
        ));
        localStorage.removeItem(CART_STORAGE_KEY);
        await renderCartState();
    } catch (error) {
        alert(error.message || "Nao foi possivel sincronizar o carrinho.");
    }
}

function formatMoney(value) {
    return Number(value || 0).toLocaleString("pt-BR", {
        style: "currency",
        currency: "BRL"
    });
}

function injectCartModal() {
    if (document.getElementById("cartOverlay")) return;

    document.body.insertAdjacentHTML("beforeend", `
        <div id="cartOverlay" class="fixed inset-0 bg-charcoal/60 backdrop-blur-sm hidden items-center justify-center z-[300] px-4">
            <div class="bg-white rounded-2xl p-8 w-full max-w-lg relative shadow-[0_30px_80px_rgba(0,0,0,0.25)] animate-slide-up max-h-[90vh] overflow-y-auto">
                <button type="button" onclick="closeCart()"
                    class="absolute top-5 right-5 w-8 h-8 rounded-full bg-cream hover:bg-border flex items-center justify-center text-sm transition-colors">
                    <i class="fa-solid fa-xmark"></i>
                </button>
                <h2 class="font-display text-2xl font-bold text-charcoal mb-1">Carrinho</h2>
                <p class="text-muted text-sm mb-7">Revise os produtos antes de comprar.</p>
                <div id="cartItems" class="space-y-4"></div>
                <div class="border-t border-border mt-6 pt-5 flex items-center justify-between">
                    <span class="font-semibold">Subtotal</span>
                    <strong id="cartSubtotal" class="text-xl"></strong>
                </div>
                <div class="grid grid-cols-2 gap-3 mt-6">
                    <button type="button" onclick="clearCart()"
                        class="border border-border hover:bg-cream rounded-xl py-3 font-semibold transition-colors">Limpar</button>
                    <button type="button" onclick="checkoutCart()"
                        class="bg-amber hover:bg-amber-light text-charcoal rounded-xl py-3 font-bold transition-colors">Comprar agora</button>
                </div>
            </div>
        </div>
    `);
}

async function openCart() {
    const overlay = document.getElementById("cartOverlay");
    if (!overlay) return;

    await renderCartState();
    overlay.classList.remove("hidden");
    overlay.classList.add("flex");
}

function closeCart() {
    const overlay = document.getElementById("cartOverlay");
    if (!overlay) return;

    overlay.classList.add("hidden");
    overlay.classList.remove("flex");
}

function normalizeBackendItem(item) {
    return {
        id: item.idItemPedido,
        productId: item.idProdutoFk,
        nome: item.nomeProduto,
        preco: Number(item.precoUnitario || 0),
        qtd: item.quantidade || 0,
        imagem: PetImages.productImageSrc(item.imagemProduto),
        backend: true
    };
}

async function getRenderableCartItems() {
    if (usesBackendCart()) {
        const { itens } = await loadBackendCart();
        return itens.map(normalizeBackendItem);
    }

    return getCart().map((item) => ({
        id: item.id_produto,
        productId: item.id_produto,
        nome: item.nome,
        preco: item.preco,
        qtd: item.qtd,
        imagem: item.imagem,
        backend: false
    }));
}

async function renderCartState() {
    const items = await getRenderableCartItems();
    const totalItems = items.reduce((total, item) => total + item.qtd, 0);
    const subtotal = items.reduce((total, item) => total + item.preco * item.qtd, 0);

    document.querySelectorAll("[data-cart-count]").forEach((el) => {
        el.textContent = String(totalItems);
        el.classList.toggle("hidden", totalItems === 0);
    });

    const cartItems = document.getElementById("cartItems");
    const cartSubtotal = document.getElementById("cartSubtotal");
    if (!cartItems || !cartSubtotal) return;

    cartSubtotal.textContent = formatMoney(subtotal);

    if (items.length === 0) {
        cartItems.innerHTML = `<p class="text-muted text-sm">Seu carrinho esta vazio.</p>`;
        return;
    }

    cartItems.innerHTML = items.map((item) => `
        <div class="flex gap-4 border border-border rounded-xl p-3">
            <img src="${item.imagem}" alt="${item.nome}" class="w-16 h-16 object-contain rounded-lg bg-cream p-1">
            <div class="flex-1 min-w-0">
                <h3 class="font-semibold text-sm truncate">${item.nome}</h3>
                <p class="text-sm text-muted">${formatMoney(item.preco)}</p>
                <div class="flex items-center gap-2 mt-2">
                    <button type="button" onclick="setQuantity(${item.id}, ${item.qtd - 1})"
                        class="w-7 h-7 border border-border rounded-lg hover:bg-cream">-</button>
                    <span class="w-8 text-center text-sm">${item.qtd}</span>
                    <button type="button" onclick="setQuantity(${item.id}, ${item.qtd + 1})"
                        class="w-7 h-7 border border-border rounded-lg hover:bg-cream">+</button>
                    <button type="button" onclick="removeItem(${item.id})"
                        class="ml-auto text-red-500 hover:text-red-600 text-sm">
                        <i class="fa-solid fa-trash"></i>
                    </button>
                </div>
            </div>
        </div>
    `).join("");
}

document.addEventListener("DOMContentLoaded", () => {
    injectCartModal();
    renderCartState();
});

window.PetCart = {
    getCart,
    addItem,
    setQuantity,
    removeItem,
    clearCart,
    checkoutCart,
    syncLocalCartToBackend,
    openCart,
    closeCart,
    renderCartState
};
