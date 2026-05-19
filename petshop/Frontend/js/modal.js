function abrir() {
  const overlay = document.getElementById("overlay");
  if (!overlay) return;
  overlay.classList.remove("hidden");
  overlay.classList.add("flex");
}

function fechar() {
  const overlay = document.getElementById("overlay");
  if (!overlay) return;
  overlay.classList.add("hidden");
  overlay.classList.remove("flex");
}

function abrirProdutos() {
  const overlay = document.getElementById("overlayP");
  if (!overlay) return;
  overlay.classList.remove("hidden");
  overlay.classList.add("flex");
}

function fecharProdutos() {
  const overlay = document.getElementById("overlayP");
  if (!overlay) return;
  overlay.classList.add("hidden");
  overlay.classList.remove("flex");
}



