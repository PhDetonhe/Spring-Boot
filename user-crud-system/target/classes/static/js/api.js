/**
 * Camada fina sobre fetch() para conversar com a API REST do backend.
 * Centraliza: URL base, headers, tratamento de erros e envio de cookies
 * de sessao (necessarios para as rotas protegidas em /api/users/**).
 */
const Api = (() => {
  const BASE_URL = '/api';

  async function request(path, { method = 'GET', body, headers = {} } = {}) {
    const response = await fetch(`${BASE_URL}${path}`, {
      method,
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
        ...headers,
      },
      body: body !== undefined ? JSON.stringify(body) : undefined,
    });

    const contentType = response.headers.get('content-type') || '';
    const isJson = contentType.includes('application/json');
    const data = isJson ? await response.json().catch(() => null) : null;

    if (!response.ok) {
      const message =
        (data && (data.message || (data.details && data.details.join(' ')))) ||
        `Erro inesperado (HTTP ${response.status}).`;
      const error = new Error(message);
      error.status = response.status;
      error.details = data && data.details;
      throw error;
    }

    return data;
  }

  return {
    get: (path) => request(path, { method: 'GET' }),
    post: (path, body) => request(path, { method: 'POST', body }),
    put: (path, body) => request(path, { method: 'PUT', body }),
    del: (path) => request(path, { method: 'DELETE' }),

    // Endpoints de autenticacao
    register: (payload) => request('/auth/register', { method: 'POST', body: payload }),
    login: (payload) => request('/auth/login', { method: 'POST', body: payload }),
    logout: () => request('/auth/logout', { method: 'POST' }),
    me: () => request('/auth/me', { method: 'GET' }),

    // CRUD de usuarios
    listUsers: () => request('/users', { method: 'GET' }),
    getUser: (id) => request(`/users/${id}`, { method: 'GET' }),
    updateUser: (id, payload) => request(`/users/${id}`, { method: 'PUT', body: payload }),
    deleteUser: (id) => request(`/users/${id}`, { method: 'DELETE' }),

    // Recuperacao de senha
    forgotPassword: (payload) => request('/password/forgot', { method: 'POST', body: payload }),
    resetPassword: (payload) => request('/password/reset', { method: 'POST', body: payload }),
  };
})();

/** Exibe uma notificacao (toast) simples no canto superior direito da tela. */
function showToast(message, type = 'info') {
  const container = document.getElementById('toast-container');
  if (!container) {
    // eslint-disable-next-line no-alert
    alert(message);
    return;
  }

  const palette = {
    success: 'bg-emerald-600',
    error: 'bg-red-600',
    info: 'bg-slate-800',
  };

  const toast = document.createElement('div');
  toast.className = `toast ${palette[type] || palette.info} text-white text-sm font-medium px-4 py-3 rounded-lg shadow-lg mb-2`;
  toast.textContent = message;
  container.appendChild(toast);

  setTimeout(() => {
    toast.remove();
  }, 4500);
}

/** Alterna estado de "carregando" em um botao de submit, com spinner. */
function setButtonLoading(button, loading, loadingText = 'Enviando...') {
  if (loading) {
    button.dataset.originalText = button.innerHTML;
    button.disabled = true;
    button.innerHTML = `<span class="inline-flex items-center gap-2"><span class="spinner"></span>${loadingText}</span>`;
  } else {
    button.disabled = false;
    button.innerHTML = button.dataset.originalText || button.innerHTML;
  }
}

/**
 * Garante que ha um usuario logado; caso contrario, redireciona para o
 * login. Retorna os dados do usuario logado quando ha sessao valida.
 * Use em paginas que exigem autenticacao (ex.: users.html).
 */
async function requireAuth() {
  try {
    return await Api.me();
  } catch (err) {
    window.location.href = '/login.html';
    return null;
  }
}
