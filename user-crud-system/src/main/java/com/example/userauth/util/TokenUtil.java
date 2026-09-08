package com.example.userauth.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utilitario para geracao de tokens aleatorios seguros (usados na
 * recuperacao de senha) e para calculo do hash SHA-256 desses tokens.
 * <p>
 * Estrategia: o token em texto puro e enviado por e-mail ao usuario e
 * NUNCA persistido; apenas o hash SHA-256 dele e salvo no banco. Na
 * validacao, o token recebido de volta e re-hasheado e comparado com o
 * valor salvo.
 */
public final class TokenUtil {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private TokenUtil() {
    }

    /** Gera um token aleatorio seguro, codificado em Base64 (URL-safe, sem padding). */
    public static String generateRawToken() {
        byte[] randomBytes = new byte[32]; // 256 bits de entropia
        SECURE_RANDOM.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    /** Calcula o hash SHA-256 (em hexadecimal) de um token em texto puro. */
    public static String hashToken(String rawToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(rawToken.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(hashBytes.length * 2);
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Algoritmo SHA-256 indisponivel", e);
        }
    }
}
