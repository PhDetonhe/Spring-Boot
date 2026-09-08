const params = new URLSearchParams(window.location.search);
const token = params.get('token');

if (!token) {
  document.getElementById('form-state').classList.add('hidden');
  document.getElementById('invalid-token-state').classList.remove('hidden');
} else {
  document.getElementById('reset-form').addEventListener('submit', async (event) => {
    event.preventDefault();

    const button = document.getElementById('reset-btn');
    const newPassword = document.getElementById('new-password').value;
    const confirmPassword = document.getElementById('confirm-password').value;

    if (newPassword !== confirmPassword) {
      showToast('As senhas informadas não coincidem.', 'error');
      return;
    }

    setButtonLoading(button, true, 'Redefinindo...');

    try {
      await Api.resetPassword({ token, newPassword });
      document.getElementById('form-state').classList.add('hidden');
      document.getElementById('success-state').classList.remove('hidden');
    } catch (err) {
      showToast(err.message, 'error');
    } finally {
      setButtonLoading(button, false);
    }
  });
}
