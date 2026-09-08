document.getElementById('login-form').addEventListener('submit', async (event) => {
  event.preventDefault();

  const button = document.getElementById('login-btn');
  const email = document.getElementById('email').value.trim();
  const password = document.getElementById('password').value;

  setButtonLoading(button, true, 'Entrando...');

  try {
    await Api.login({ email, password });
    showToast('Login realizado com sucesso!', 'success');
    window.location.href = '/users.html';
  } catch (err) {
    showToast(err.message, 'error');
  } finally {
    setButtonLoading(button, false);
  }
});

// Se já existir uma sessão ativa, pula direto para a área logada.
(async () => {
  try {
    await Api.me();
    window.location.href = '/users.html';
  } catch (_) {
    // sem sessão ativa, permanece na tela de login
  }
})();
