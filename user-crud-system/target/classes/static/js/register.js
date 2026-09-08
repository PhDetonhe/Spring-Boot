document.getElementById('register-form').addEventListener('submit', async (event) => {
  event.preventDefault();

  const button = document.getElementById('register-btn');
  const name = document.getElementById('name').value.trim();
  const email = document.getElementById('email').value.trim();
  const password = document.getElementById('password').value;

  setButtonLoading(button, true, 'Criando conta...');

  try {
    await Api.register({ name, email, password });
    showToast('Conta criada com sucesso! Faça login para continuar.', 'success');
    setTimeout(() => {
      window.location.href = '/login.html';
    }, 1200);
  } catch (err) {
    showToast(err.message, 'error');
  } finally {
    setButtonLoading(button, false);
  }
});
