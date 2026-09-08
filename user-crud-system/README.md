# UserHub — CRUD de Usuários com Login e Recuperação de Senha

Sistema completo de cadastro de usuários (CRUD), autenticação (login) e
recuperação de senha ("esqueci minha senha") com envio de e-mail pelo
[Resend](https://resend.com). Back-end em **Spring Boot** + **MySQL**,
front-end estático em **HTML + Tailwind CSS + JavaScript (fetch)**.

## Principais características

- **CRUD de usuários**: cadastrar, listar, editar e excluir usuários.
- **Login** com verificação de e-mail/senha.
- **Senhas em hash**: nunca armazenadas em texto puro — usa **BCrypt**
  (via `spring-security-crypto`).
- **Esqueci minha senha**: gera um token aleatório, salva **apenas o hash
  SHA-256** desse token no banco (com expiração de 30 min por padrão) e
  envia o link de redefinição por e-mail via **Resend**.
- **Sessão HTTP** simples (cookie de sessão) protegendo as rotas de
  gerenciamento de usuários.
- Front-end 100% estático, servido pelo próprio Spring Boot a partir de
  `src/main/resources/static`.

## Stack

| Camada        | Tecnologia                                             |
|---------------|---------------------------------------------------------|
| Back-end      | Java 17, Spring Boot 3.3, Spring Data JPA, Bean Validation |
| Banco de dados| MySQL 8                                                 |
| Hash de senha | BCrypt (`spring-security-crypto`)                       |
| E-mail        | API HTTP do Resend (`https://api.resend.com/emails`)     |
| Front-end     | HTML5, Tailwind CSS (via CDN), JavaScript (fetch API)    |

## Estrutura de pastas

```
user-crud-system/
├── pom.xml
├── README.md
└── src/main/
    ├── java/com/example/userauth/
    │   ├── UserAuthApplication.java
    │   ├── config/          # Beans (BCrypt, RestTemplate) e filtro de sessão
    │   ├── controller/      # AuthController, UserController, PasswordResetController
    │   ├── dto/             # Objetos de entrada/saída da API
    │   ├── entity/          # User, PasswordResetToken (JPA)
    │   ├── exception/       # Exceções de negócio + handler global
    │   ├── repository/      # Interfaces Spring Data JPA
    │   ├── service/         # Regras de negócio (UserService, AuthService, EmailService, PasswordResetService)
    │   └── util/            # Geração/hash de tokens (TokenUtil)
    └── resources/
        ├── application.properties
        ├── db/schema.sql    # Schema de referência (opcional)
        └── static/          # Front-end (HTML, CSS, JS)
            ├── index.html
            ├── login.html
            ├── register.html
            ├── forgot-password.html
            ├── reset-password.html
            ├── users.html
            ├── css/styles.css
            └── js/ (api.js, login.js, register.js, forgot-password.js, reset-password.js, users.js)
```

## Pré-requisitos

- Java 17+ (JDK instalado e configurado no Eclipse)
- Eclipse com suporte a Maven (já vem integrado no Eclipse IDE for
  Enterprise Java / basta `File > Import > Existing Maven Projects`)
- XAMPP com o MySQL (módulo "MySQL") iniciado
- Uma conta gratuita no [Resend](https://resend.com) e uma API Key

## Configuração

### 1. Suba o MySQL pelo XAMPP

Abra o painel do XAMPP e clique em **Start** ao lado de **MySQL**. Por
padrão, o XAMPP expõe o MySQL em `localhost:3306` com usuário `root` e
**sem senha** — é exatamente o que já está configurado no
`application.properties` deste projeto. Não é necessário criar o banco
manualmente: a aplicação cria o schema `user_crud_db` sozinha na
primeira execução (`createDatabaseIfNotExist=true`) e o Hibernate cria
as tabelas automaticamente (`ddl-auto=update`) — **sem Flyway, sem
scripts de migração**, mantendo tudo simples.

Se preferir usar o **phpMyAdmin** do XAMPP para conferir os dados depois
de rodar a aplicação uma vez, acesse `http://localhost/phpmyadmin` e
procure pelo banco `user_crud_db`.

### 2. Edite `src/main/resources/application.properties`

```properties
# Banco de dados (padrao XAMPP)
spring.datasource.url=jdbc:mysql://localhost:3306/user_crud_db?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=

# Resend
resend.api.key=re_SUA_API_KEY_AQUI
resend.from.email=Sistema de Usuarios <onboarding@resend.dev>

# Link de redefinição de senha (ajuste a porta/host se necessário)
app.reset-password.base-url=http://localhost:8080/reset-password.html
```

> **Dica:** enquanto seu domínio não estiver verificado no Resend, use o
> remetente de testes `onboarding@resend.dev` — ele funciona sem
> configuração de DNS, mas só envia para o e-mail cadastrado na sua conta
> Resend. Para enviar a qualquer destinatário, verifique um domínio
> próprio no painel do Resend e troque `resend.from.email`.

## Como importar e executar no Eclipse

1. Extraia o `.zip` em uma pasta de sua preferência.
2. No Eclipse: **File → Import… → Maven → Existing Maven Projects**.
3. Clique em **Browse**, selecione a pasta `user-crud-system` (a que
   contém o `pom.xml`) e finalize a importação. O Eclipse baixa as
   dependências do Maven Central automaticamente na primeira vez
   (é necessário ter internet nesse momento).
4. Confirme que o **Project Facet** está usando **Java 17** (clique
   direito no projeto → **Properties → Java Build Path / Java Compiler**).
5. Com o MySQL do XAMPP já iniciado, clique direito em
   `UserAuthApplication.java` → **Run As → Java Application**.
6. O console deve mostrar `Tomcat started on port(s): 8080`.

A aplicação sobe em `http://localhost:8080`. Abra essa URL no navegador —
você será redirecionado para a tela de login (ou cadastro).

### Alternativa via terminal (se preferir usar o Maven do Eclipse ou um Maven instalado à parte)

```bash
cd user-crud-system
mvn spring-boot:run
```

## Fluxo de uso

1. Acesse `/register.html` e crie uma conta.
2. Faça login em `/login.html`.
3. Você será redirecionado para `/users.html`, onde pode listar, editar
   e excluir usuários cadastrados.
4. Em `/login.html`, clique em **"Esqueci minha senha"** para testar o
   fluxo de recuperação: informe o e-mail cadastrado, verifique sua caixa
   de entrada (o Resend enviará o e-mail) e clique no link recebido para
   definir uma nova senha em `/reset-password.html`.

## Endpoints da API

| Método | Rota                     | Descrição                              | Autenticação |
|--------|---------------------------|-----------------------------------------|--------------|
| POST   | `/api/auth/register`      | Cadastra um novo usuário                | Pública      |
| POST   | `/api/auth/login`         | Autentica e cria sessão                 | Pública      |
| POST   | `/api/auth/logout`        | Encerra a sessão atual                  | Pública      |
| GET    | `/api/auth/me`            | Retorna o usuário logado                | Sessão       |
| GET    | `/api/users`               | Lista todos os usuários                 | Sessão       |
| GET    | `/api/users/{id}`          | Busca um usuário pelo id                | Sessão       |
| PUT    | `/api/users/{id}`          | Atualiza nome/e-mail/senha              | Sessão       |
| DELETE | `/api/users/{id}`          | Exclui um usuário                       | Sessão       |
| POST   | `/api/password/forgot`     | Solicita o link de recuperação de senha | Pública      |
| POST   | `/api/password/reset`      | Redefine a senha usando o token         | Pública      |

Todas as respostas de erro seguem o formato:

```json
{
  "timestamp": "2026-08-27T10:00:00",
  "status": 400,
  "error": "Dados invalidos",
  "message": "Verifique os campos informados",
  "path": "/api/auth/register",
  "details": ["A senha deve ter entre 6 e 100 caracteres"]
}
```

## Segurança implementada

- Senhas de usuário: hash **BCrypt** (custo adaptativo, salt automático).
- Tokens de recuperação de senha: **nunca** armazenados em texto puro —
  apenas o hash **SHA-256** do token é salvo; o valor original só existe
  no link enviado por e-mail.
- Tokens de recuperação expiram (padrão: 30 minutos) e só podem ser
  usados **uma vez**.
- Ao solicitar recuperação de senha para um e-mail inexistente, a API
  responde com uma mensagem genérica de sucesso, evitando enumeração de
  contas cadastradas.
- Rotas de gerenciamento de usuários (`/api/users/**`) exigem sessão
  autenticada (`SessionAuthFilter`).
- Um novo login sempre invalida a sessão anterior (mitiga fixação de
  sessão).

## Observação sobre o build

Este pacote foi gerado em um ambiente sem acesso ao Maven Central, então
o build (`mvn spring-boot:run` / `mvn package`) não pôde ser validado
neste momento — ele deve ser executado normalmente na sua máquina, que
terá acesso à internet para baixar as dependências declaradas no
`pom.xml`. O código segue as APIs estáveis do Spring Boot 3.3 / Spring
Data JPA / Jakarta Validation.

## Próximos passos sugeridos

- Adicionar paginação e busca na listagem de usuários.
- Trocar a sessão HTTP por JWT, caso o front-end precise ser desacoplado
  em outro domínio/app.
- Adicionar testes automatizados (JUnit + Mockito / Testcontainers).
- Verificar um domínio próprio no Resend para envio de e-mail em produção.

## Nota para quando for publicar no Render

Este README cobre apenas o ambiente local (Eclipse + XAMPP). Quando for
implementar o deploy no [Render](https://render.com), os pontos que
provavelmente precisarão de ajuste são:

- Trocar `spring.datasource.url/username/password` por variáveis de
  ambiente (o Render injeta `DATABASE_URL` ou similar, dependendo do
  banco gerenciado escolhido).
- Definir `resend.api.key` como variável de ambiente, em vez de deixar
  a chave escrita no `application.properties`.
- Atualizar `app.reset-password.base-url` para a URL pública do serviço
  no Render (ex.: `https://seu-app.onrender.com/reset-password.html`).
- Confirmar que a porta do servidor respeita a variável `PORT` fornecida
  pelo Render (`server.port=${PORT:8080}`).

Nenhuma dessas mudanças é necessária agora — fica só como referência
para quando você chegar nessa etapa.
