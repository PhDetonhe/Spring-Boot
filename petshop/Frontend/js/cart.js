const CART_STORAGE_KEY = "petshop_cart";

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

function cartItemFromProduct(product, qtd = 1) {
    const price = Number(product.preco_desconto || product.preco || 0);

    return {
        id_produto: product.id_produto,
        nome: product.nome,
        preco: price,
        imagem: product.imagem || "https://via.placeholder.com/150",
        qtd,
        qtd_estoque: product.qtd_estoque ?? null
    };
}

function addItem(product, qtd = 1) {
    if (!product?.id_produto) return;

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

function setQuantity(id, qtd) {
    const items = getCart();
    const item = items.find((cartItem) => cartItem.id_produto === id);
    if (!item) return;

    const nextQtd = Math.max(1, Number(qtd) || 1);
    item.qtd = item.qtd_estoque !== null ? Math.min(nextQtd, item.qtd_estoque) : nextQtd;
    saveCart(items);
}

function removeItem(id) {
    saveCart(getCart().filter((item) => item.id_produto !== id));
}

function clearCart() {
    saveCart([]);
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
                    <button type="button" onclick="alert('Compra simulada com sucesso!'); clearCart(); closeCart();"
                        class="bg-amber hover:bg-amber-light text-charcoal rounded-xl py-3 font-bold transition-colors">Comprar agora</button>
                </div>
            </div>
        </div>
    `);
}

function openCart() {
    const overlay = document.getElementById("cartOverlay");
    if (!overlay) return;

    renderCartState();
    overlay.classList.remove("hidden");
    overlay.classList.add("flex");
}

function closeCart() {
    const overlay = document.getElementById("cartOverlay");
    if (!overlay) return;

    overlay.classList.add("hidden");
    overlay.classList.remove("flex");
}

function renderCartState() {
    const items = getCart();
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
            <img src="${item.imagem}" alt="${item.nome}" class="w-16 h-16 object-contain rounded-lg bg-cream">
            <div class="flex-1 min-w-0">
                <h3 class="font-semibold text-sm truncate">${item.nome}</h3>
                <p class="text-sm text-muted">${formatMoney(item.preco)}</p>
                <div class="flex items-center gap-2 mt-2">
                    <button type="button" onclick="setQuantity(${item.id_produto}, ${item.qtd - 1})"
                        class="w-7 h-7 border border-border rounded-lg hover:bg-cream">-</button>
                    <span class="w-8 text-center text-sm">${item.qtd}</span>
                    <button type="button" onclick="setQuantity(${item.id_produto}, ${item.qtd + 1})"
                        class="w-7 h-7 border border-border rounded-lg hover:bg-cream">+</button>
                    <button type="button" onclick="removeItem(${item.id_produto})"
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
    openCart,
    closeCart,
    renderCartState
};
