-- ============================================================
-- Schema de referencia do banco MySQL.
-- OBSERVACAO: por padrao, a aplicacao usa spring.jpa.hibernate.ddl-auto=update
-- e cria/atualiza estas tabelas automaticamente ao subir. Este script e
-- fornecido apenas como referencia/documentacao, ou para quem preferir
-- criar o schema manualmente (nesse caso, ajuste ddl-auto para "validate"
-- ou "none" no application.properties).
-- ============================================================

CREATE DATABASE IF NOT EXISTS user_crud_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE user_crud_db;

CREATE TABLE IF NOT EXISTS users (
  id          BIGINT AUTO_INCREMENT PRIMARY KEY,
  name        VARCHAR(150) NOT NULL,
  email       VARCHAR(150) NOT NULL UNIQUE,
  password    VARCHAR(255) NOT NULL,           -- hash BCrypt, nunca texto puro
  created_at  DATETIME NOT NULL,
  updated_at  DATETIME NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS password_reset_tokens (
  id           BIGINT AUTO_INCREMENT PRIMARY KEY,
  token_hash   VARCHAR(255) NOT NULL UNIQUE,   -- hash SHA-256 do token, nunca o token em si
  user_id      BIGINT NOT NULL,
  expiry_date  DATETIME NOT NULL,
  used         BOOLEAN NOT NULL DEFAULT FALSE,
  created_at   DATETIME NOT NULL,
  CONSTRAINT fk_password_reset_tokens_user
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE
) ENGINE=InnoDB;
