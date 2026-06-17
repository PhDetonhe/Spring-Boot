// Sistema de busca funcional

function initializeSearch() {
    const searchInput = document.querySelector('.hidden.md\\:flex input[type="text"]');
    const searchButton = document.querySelector('.hidden.md\\:flex button');

    if (!searchInput || !searchButton) return;

    // Função para executar busca
    function executeSearch() {
        const term = searchInput.value.trim();
        if (term.length === 0) {
            alert('Digite um termo de busca');
            return;
        }

        // Redirecionar para página de produtos com termo de busca
        window.location.href = `produtos.html?search=${encodeURIComponent(term)}`;
    }

    // Buscar ao clicar no botão
    searchButton.addEventListener('click', executeSearch);

    // Buscar ao pressionar Enter
    searchInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            executeSearch();
        }
    });
}

// Carregar ao iniciar
document.addEventListener('DOMContentLoaded', initializeSearch);

// Se o script carregar depois do DOM estar pronto
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initializeSearch);
} else {
    initializeSearch();
}
