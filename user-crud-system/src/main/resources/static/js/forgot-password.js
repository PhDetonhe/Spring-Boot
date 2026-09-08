document.getElementById('forgot-form').addEventListener('submit', async (event) => {
  event.preventDefault();

  const button = document.getElementById('forgot-btn');
  const email = document.getElementById('email').value.trim();

  setButtonLoading(button, true, 'Enviando...');

  try {
    const result = await Api.forgotPassword({ email });
    document.getElementById('form-state').classList.add('hidden');
    document.getElementById('success-state').classList.remove('hidden');
    showToast(result.message, 'success');
  } catch (err) {
    showToast(err.message, 'error');
  } finally {
    setButtonLoading(button, false);
  }
});
