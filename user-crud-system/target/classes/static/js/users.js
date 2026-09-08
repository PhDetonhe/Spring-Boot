let currentUser = null;
let usersCache = [];

function formatDate(isoString) {
  if (!isoString) return '-';
  const date = new Date(isoString);
  return date.toLocaleDateString('pt-BR', { day: '2-digit', month: '2-digit', year: 'numeric' });
}

function escapeHtml(value) {
  const div = document.createElement('div');
  div.textContent = value ?? '';
  return div.innerHTML;
}

function renderUsers(users) {
  const tbody = document.getElementById('users-table-body');
  const emptyState = document.getElementById('empty-state');

  if (!users.length) {
    tbody.innerHTML = '';
    emptyState.classList.remove('hidden');
    return;
  }

  emptyState.classList.add('hidden');

  tbody.innerHTML = users.map((user) => `
    <tr class="hover:bg-slate-50">
      <td class="px-6 py-4 font-medium text-slate-800">${escapeHtml(user.name)}</td>
      <td class="px-6 py-4 text-slate-600">${escapeHtml(user.email)}</td>
      <td class="px-6 py-4 text-slate-500">${formatDate(user.createdAt)}</td>
      <td class="px-6 py-4 text-right space-x-2">
        <button data-action="edit" data-id="${user.id}"
          class="text-indigo-600 hover:text-indigo-800 font-medium text-sm">Editar</button>
        <button data-action="delete" data-id="${user.id}"
          class="text-red-600 hover:text-red-800 font-medium text-sm">Excluir</button>
      </td>
    </tr>
  `).join('');
}

async function loadUsers() {
  try {
    usersCache = await Api.listUsers();
    renderUsers(usersCache);
  } catch (err) {
    showToast(err.message, 'error');
  }
}

// ---------- Modal de edição ----------
const editModal = document.getElementById('edit-modal');
const editForm = document.getElementById('edit-form');

function openEditModal(user) {
  document.getElementById('edit-id').value = user.id;
  document.getElementById('edit-name').value = user.name;
  document.getElementById('edit-email').value = user.email;
  document.getElementById('edit-password').value = '';
  editModal.classList.remove('hidden');
}

function closeEditModal() {
  editModal.classList.add('hidden');
  editForm.reset();
}

document.getElementById('edit-cancel-btn').addEventListener('click', closeEditModal);

editForm.addEventListener('submit', async (event) => {
  event.preventDefault();
  const saveBtn = document.getElementById('edit-save-btn');

  const id = document.getElementById('edit-id').value;
  const payload = {
    name: document.getElementById('edit-name').value.trim(),
    email: document.getElementById('edit-email').value.trim(),
  };
  const password = document.getElementById('edit-password').value;
  if (password) {
    payload.password = password;
  }

  setButtonLoading(saveBtn, true, 'Salvando...');
  try {
    await Api.updateUser(id, payload);
    showToast('Usuário atualizado com sucesso!', 'success');
    closeEditModal();
    loadUsers();
  } catch (err) {
    showToast(err.message, 'error');
  } finally {
    setButtonLoading(saveBtn, false);
  }
});

// ---------- Modal de exclusão ----------
const deleteModal = document.getElementById('delete-modal');
let userIdPendingDelete = null;

function openDeleteModal(user) {
  userIdPendingDelete = user.id;
  document.getElementById('delete-user-name').textContent = user.name;
  deleteModal.classList.remove('hidden');
}

function closeDeleteModal() {
  userIdPendingDelete = null;
  deleteModal.classList.add('hidden');
}

document.getElementById('delete-cancel-btn').addEventListener('click', closeDeleteModal);

document.getElementById('delete-confirm-btn').addEventListener('click', async () => {
  if (!userIdPendingDelete) return;
  const btn = document.getElementById('delete-confirm-btn');

  setButtonLoading(btn, true, 'Excluindo...');
  try {
    await Api.deleteUser(userIdPendingDelete);
    showToast('Usuário excluído com sucesso!', 'success');
    closeDeleteModal();
    loadUsers();
  } catch (err) {
    showToast(err.message, 'error');
  } finally {
    setButtonLoading(btn, false);
  }
});

// ---------- Delegação de cliques na tabela ----------
document.getElementById('users-table-body').addEventListener('click', (event) => {
  const button = event.target.closest('button[data-action]');
  if (!button) return;

  const id = Number(button.dataset.id);
  const user = usersCache.find((u) => u.id === id);
  if (!user) return;

  if (button.dataset.action === 'edit') {
    openEditModal(user);
  } else if (button.dataset.action === 'delete') {
    openDeleteModal(user);
  }
});

// ---------- Logout ----------
document.getElementById('logout-btn').addEventListener('click', async () => {
  try {
    await Api.logout();
  } finally {
    window.location.href = '/login.html';
  }
});

// ---------- Inicialização ----------
(async () => {
  currentUser = await requireAuth();
  if (!currentUser) return; // requireAuth já redireciona para /login.html

  document.getElementById('current-user-name').textContent = currentUser.name;
  await loadUsers();
})();
