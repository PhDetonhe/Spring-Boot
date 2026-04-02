fetch("http://localhost:8080/categorias")
    .then(response => response.json())
    .then(data => {
        const tabela = document.getElementById("sectionscategorias");

        data.forEach(categoria => {
            const linha = `
          <div class="bg-slate-800 p-5 rounded-xl w-72 h-72 mx-5 my-6 border border-slate-700 
            flex flex-col justify-between
            transition-all duration-300 ease-out 
            hover:scale-110 hover:-translate-y-1 hover:shadow-2xl hover:border-blue-500/50">

    <!-- conteúdo -->
    <div>
        <h2 class="text-lg font-semibold text-blue-300 mb-1 truncate">
            ${categoria.nome}
        </h2>

        <p class="text-sm text-slate-400 leading-relaxed line-clamp-3">
            ${categoria.descricao}
        </p>
    </div>

    <!-- botões -->
    <div class="flex justify-between items-center mt-4">

        <button onclick="editar()" 
            class="flex items-center gap-1 text-xs bg-blue-500/10 text-blue-400 px-3 py-1.5 rounded-lg 
            hover:bg-blue-500/20 transition">
            <i class="fa-solid fa-pen"></i>
            Editar
        </button>

        <button onclick="excluir(${categoria.id_categoria}, this)" 
            class="flex items-center gap-1 text-xs bg-red-500/10 text-red-400 px-3 py-1.5 rounded-lg 
            hover:bg-red-500/20 transition">
            <i class="fa-solid fa-trash"></i>
            Excluir
        </button>

    </div>

</div>
            `;
            tabela.innerHTML += linha;
        });
    })
    .catch(error => console.error("Erro:", error));








    fetch("http://localhost:8080/categorias")
    .then(response => response.json())
    .then(data => {
        const nav = document.getElementById("categoriasnav");

        nav.innerHTML = ""; // limpa antes

        data.forEach(categoria => {
            const item = `
                <button 
                   class="text-blue-900 text-2xl my-2 font-bold transition-all duration-300 ease-out hover:text-white hover:scale-105 hover:-translate-y-0.5"
                    onclick="filtrarPorCategoria('${categoria.id}')"
                >
                    ${categoria.nome}
                </button>
            `;

            nav.innerHTML += item;
        });
    })
    .catch(error => console.error("Erro:", error));


    fetch("http://localhost:8080/categorias")
    .then(response => response.json())
    .then(data => {
        const nav = document.getElementById("catfooter");

        nav.innerHTML = ""; 

        data.forEach(categoria => {
            const item = `
                <li class="mb-4">
                    <a href="" class="hover:underline">${categoria.nome}</a>
                </li>
            `;

            nav.innerHTML += item;
        });
    })
    .catch(error => console.error("Erro:", error));