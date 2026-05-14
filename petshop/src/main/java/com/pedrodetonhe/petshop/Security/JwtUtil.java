package com.pedrodetonhe.petshop.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Chave secreta - em produção, coloque no application.properties
    private static final String SECRET = "petshop-secret-key-super-segura-2024-petshop";
    private static final long EXPIRATION_MS = 86400000; // 24 horas

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // Gera o token JWT com email e role
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extrai o email (subject) do token
    public String extractEmail(String token) {
        return parseClaims(token).getSubject();
    }

    // Extrai a role do token
    public String extractRole(String token) {
        return (String) parseClaims(token).get("role");
    }

    // Valida se o token é válido
    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}