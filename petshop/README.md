# 🐾 PetShop — Sistema de E-commerce

Sistema para pet shop com backend em **Spring Boot** e frontend em **HTML + Tailwind CSS**, com autenticação JWT e controle de acesso por roles.

-----------------------------------------------------------

## 🔧 Tecnologias

### Backend
- **Java 17+** com **Spring Boot**
- **Spring Security** — autenticação stateless com JWT
- **Spring Data JPA** — acesso a banco de dados
- **BCrypt** — hash de senhas
- **JJWT** — geração e validação de tokens JWT

### Frontend
- **HTML5**
- **Tailwind CSS** (via CDN)
- **Font Awesome 7** (ícones)
- **JavaScript puro** (Fetch API)

---

## 🔐 Autenticação e Autorização

O sistema usa **JWT (JSON Web Token)** com dois níveis de acesso:

| Role  | Permissões |
|-------|-----------|
| `USER` | Ver produtos e categorias, editar próprios dados |
| `ADMIN` | Tudo acima + criar/editar/excluir produtos e categorias, gerenciar usuários |

### Endpoints de autenticação

| Método | Rota | Acesso | Descrição |
|--------|------|--------|-----------|
| `POST` | `/auth/cadastro` | Público | Cadastra um usuário com role `USER` |
| `POST` | `/auth/cadastro/admin` | Apenas ADMIN | Cadastra um usuário com role `ADMIN` |
| `POST` | `/auth/login` | Público | Realiza login e retorna o token JWT |

O token deve ser enviado no header das requisições protegidas:
```
Authorization: Bearer <token>
```

---

## 📦 Endpoints da API

### Categorias `/categorias`

| Método | Rota | Acesso | Descrição |
|--------|------|--------|-----------|
| `GET` | `/categorias` | Público | Lista todas as categorias |
| `GET` | `/categorias/{id}` | Público | Busca uma categoria por ID |
| `POST` | `/categorias` | ADMIN | Cria uma nova categoria |
| `PUT` | `/categorias/{id}` | ADMIN | Atualiza uma categoria |
| `DELETE` | `/categorias/{id}` | ADMIN | Remove uma categoria |

### Produtos `/produtos`

| Método | Rota | Acesso | Descrição |
|--------|------|--------|-----------|
| `GET` | `/produtos` | Público | Lista todos os produtos |
| `GET` | `/produtos/{id}` | Público | Busca um produto por ID |
| `POST` | `/produtos/{idCategoria}` | ADMIN | Cria um produto vinculado a uma categoria |
| `PUT` | `/produtos/{id}/{idCategoria}` | ADMIN | Atualiza um produto |
| `DELETE` | `/produtos/{id}` | ADMIN | Remove um produto |

### Usuários `/usuarios`

| Método | Rota | Acesso | Descrição |
|--------|------|--------|-----------|
| `GET` | `/usuarios` | ADMIN | Lista todos os usuários |
| `GET` | `/usuarios/{id}` | Autenticado | Busca usuário por ID |
| `PUT` | `/usuarios/{id}` | Autenticado | Atualiza dados do usuário |
| `DELETE` | `/usuarios/{id}` | ADMIN | Remove um usuário |

---

## 🗄️ Modelos de Dados

### Usuário
```json
{
  "id_usuario": 1,
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "<hash bcrypt>",
  "role": "USER"
}
```

### Categoria
```json
{
  "id_categoria": 1,
  "nome": "Rações",
  "descricao": "Alimentos para pets",
  "ativo": true
}
```

### Produto
```json
{
  "id_produto": 1,
  "nome": "Ração Premium",
  "descricao": "Para cães adultos",
  "preco": 89.90,
  "preco_desconto": 79.90,
  "imagem": "<base64 ou URL>",
  "qtd_estoque": 50,
  "categoria": { "id_categoria": 1, "nome": "Rações" }
}
```

---

## 🖥️ Páginas do Frontend

### `main.html` — Página Principal
- Exibe banner de destaque e grade de categorias
- Administradores veem o botão **"Nova categoria"** para abrir o modal de cadastro
- Categorias são clicáveis e redirecionam para a listagem de produtos

### `produtos.html` — Listagem de Produtos
- Filtra produtos pela categoria selecionada (via query string `?categoria=ID`)
- Administradores veem o botão **"Novo produto"**
- Cada produto tem botão de adicionar ao carrinho

### `Infoprodutos.html` — Detalhes do Produto
- Exibe imagem, nome, preço, descrição e categoria
- Controle de quantidade com validação de estoque
- Botões **"Carrinho"** e **"Comprar agora"**

---

## 🛒 Carrinho de Compras

Gerenciado pelo módulo `cart.js` com persistência em `localStorage`:

- Adicionar/remover itens
- Ajustar quantidade (respeitando o estoque)
- Calcular subtotal
- Limpar carrinho
- Modal de revisão antes de finalizar

---

## 🚀 Como Executar

### Backend

1. Configure o banco de dados no `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/petshop
spring.datasource.username=root
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

2. Execute a aplicação:
```bash
./mvnw spring-boot:run
```

O servidor iniciará em `http://localhost:8080`.

### Frontend

Abra os arquivos HTML diretamente no navegador ou sirva com qualquer servidor estático. Certifique-se de que o backend está rodando em `http://localhost:8080`.

---

## ⚙️ Configurações de Segurança

- **CORS** liberado para todas as origens (`*`) — ajustar em produção
- **Sessão stateless** — nenhum estado é mantido no servidor
- **Chave JWT** definida em `JwtUtil.java` — mover para variável de ambiente em produção:
  ```
  JWT_SECRET=sua-chave-secreta-segura
  ```
- Token expira em **24 horas**

---

## 📌 Observações

- O campo `imagem` do produto aceita base64 (`LONGTEXT` no banco) ou URL externa
- O cadastro público sempre cria usuários com role `USER`; apenas admins podem criar outros admins
- Erros de runtime retornam HTTP 400 com a mensagem descritiva; acesso negado retorna 403
