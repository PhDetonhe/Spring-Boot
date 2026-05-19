fetch("http://localhost:8080/categorias")
    .then(response => response.json())
    .then(data => {
        const nav = document.getElementById("categoriasnav");
        if (!nav) return;

        nav.innerHTML = "";

        data.forEach(categoria => {
            const item = `
                <button
                   class="text-blue-900 text-2xl my-2 font-bold transition-all duration-300 ease-out hover:text-white hover:scale-105 hover:-translate-y-0.5"
                    onclick="abrirCategoria('${categoria.id_categoria}')"
                >
                    ${categoria.nome}
                </button>
            `;

            nav.innerHTML += item;
        });
    })
    .catch(error => console.error("Erro:", error));
