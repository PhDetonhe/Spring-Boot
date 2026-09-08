package com.example.userauth.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro simples que protege os endpoints de gerenciamento de usuarios
 * (/api/users/**), exigindo uma sessao autenticada (criada no login).
 * <p>
 * Os endpoints publicos (registro, login, esqueci minha senha, reset de
 * senha e os arquivos estaticos do front-end) nao passam por essa checagem.
 */
@Component
@Order(1)
public class SessionAuthFilter extends OncePerRequestFilter {

    private static final String SESSION_USER_ID = "userId";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        boolean isProtected = path.startsWith("/api/users");

        if (isProtected) {
            HttpSession session = request.getSession(false);
            Object userId = session != null ? session.getAttribute(SESSION_USER_ID) : null;

            if (userId == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(
                        "{\"status\":401,\"error\":\"Unauthorized\",\"message\":\"Faca login para continuar.\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
