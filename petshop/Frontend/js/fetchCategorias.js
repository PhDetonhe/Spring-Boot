function abrirCategoria(id) {
    window.location.href = `produtos.html?categoria=${id}`;
}

function categoriaCard(categoria) {
    const adminActions = PetAuth.isAdmin() ? `
        <div class="flex justify-between items-center mt-4">
            <button onclick="event.stopPropagation(); alert('Edicao de categoria ainda nao foi implementada.')"
                class="flex items-center gap-1 text-xs bg-blue-500/10 text-blue-700 px-3 py-1.5 rounded-lg hover:bg-blue-500/20 transition">
                <i class="fa-solid fa-pen"></i>
                Editar
            </button>
            <button onclick="event.stopPropagation(); excluir(${categoria.id_categoria}, this)"
                class="flex items-center gap-1 text-xs bg-red-500/10 text-red-600 px-3 py-1.5 rounded-lg hover:bg-red-500/20 transition">
                <i class="fa-solid fa-trash"></i>
                Excluir
            </button>
        </div>
    ` : "";

    return `
        <div onclick="abrirCategoria(${categoria.id_categoria})"
            class="bg-white p-5 rounded-xl min-h-56 border border-border flex flex-col justify-between shadow-sm transition-all duration-300 ease-out hover:scale-[1.03] hover:-translate-y-1 hover:shadow-xl hover:border-teal-light cursor-pointer">
            <div>
                <h2 class="text-lg font-semibold text-teal mb-1 truncate">${categoria.nome}</h2>
                <p class="text-sm text-muted leading-relaxed line-clamp-3">${categoria.descricao || "Sem descricao"}</p>
            </div>
            ${adminActions}
        </div>
    `;
}

function renderCategorias(data) {
    const tabela = document.getElementById("sectionscategorias");
    if (tabela) {
        tabela.innerHTML = data.map(categoriaCard).join("");
    }

    const nav = document.getElementById("categoriasnav");
    if (nav) {
        nav.innerHTML = data.map((categoria) => `
            <button
                class="text-white/85 text-sm py-3 font-bold transition-all duration-300 ease-out hover:text-amber-light hover:-translate-y-0.5 whitespace-nowrap"
                onclick="abrirCategoria(${categoria.id_categoria})">
                ${categoria.nome}
            </button>
        `).join("");
    }

    const footer = document.getElementById("catfooter");
    if (footer) {
        footer.innerHTML = data.map((categoria) => `
            <li>
                <a href="produtos.html?categoria=${categoria.id_categoria}" class="hover:underline">${categoria.nome}</a>
            </li>
        `).join("");
    }
}

function carregarCategorias() {
    fetch("http://localhost:8080/categorias")
        .then(response => {
            if (!response.ok) throw new Error("Erro ao carregar categorias");
            return response.json();
        })
        .then(renderCategorias)
        .catch(error => {
            console.error("Erro:", error);
            const tabela = document.getElementById("sectionscategorias");
            if (tabela) tabela.innerHTML = `<p class="text-red-500">Erro ao carregar categorias</p>`;
        });
}

document.addEventListener("DOMContentLoaded", carregarCategorias);
