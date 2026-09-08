package com.example.userauth.service;

import com.example.userauth.dto.ForgotPasswordDTO;
import com.example.userauth.dto.ResetPasswordDTO;
import com.example.userauth.entity.PasswordResetToken;
import com.example.userauth.entity.User;
import com.example.userauth.exception.BusinessException;
import com.example.userauth.repository.PasswordResetTokenRepository;
import com.example.userauth.repository.UserRepository;
import com.example.userauth.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Regras de negocio do fluxo "esqueci minha senha":
 * 1) forgotPassword: gera um token aleatorio, salva SOMENTE o hash dele
 *    no banco (com prazo de validade) e envia o token em texto puro por
 *    e-mail (via Resend) dentro de um link para a tela de redefinicao.
 * 2) resetPassword: recebe o token do link, re-calcula o hash, localiza o
 *    registro correspondente, valida expiracao/uso e atualiza a senha do
 *    usuario (tambem em hash, via BCrypt).
 */
@Slf4j
@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Value("${app.reset-password.base-url}")
    private String resetPasswordBaseUrl;

    @Value("${app.reset-password.token-expiry-minutes}")
    private long tokenExpiryMinutes;

    public PasswordResetService(UserRepository userRepository,
                                 PasswordResetTokenRepository tokenRepository,
                                 PasswordEncoder passwordEncoder,
                                 EmailService emailService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Transactional
    public void forgotPassword(ForgotPasswordDTO dto) {
        String normalizedEmail = dto.getEmail().trim().toLowerCase();
        Optional<User> userOpt = userRepository.findByEmail(normalizedEmail);

        // Por seguranca (evitar enumeracao de e-mails cadastrados), sempre
        // respondemos com sucesso ao chamador, mesmo se o e-mail nao existir.
        // O envio do e-mail e o token so sao criados quando o usuario existe de fato.
        if (userOpt.isEmpty()) {
            log.info("Solicitacao de recuperacao de senha para e-mail nao cadastrado: {}", normalizedEmail);
            return;
        }

        User user = userOpt.get();

        // Invalida quaisquer tokens ativos anteriores desse usuario.
        tokenRepository.invalidateAllActiveTokensForUser(user);

        String rawToken = TokenUtil.generateRawToken();
        String tokenHash = TokenUtil.hashToken(rawToken);

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .tokenHash(tokenHash)
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(tokenExpiryMinutes))
                .used(false)
                .build();

        tokenRepository.save(resetToken);

        String resetLink = resetPasswordBaseUrl + "?token=" + rawToken;
        emailService.sendPasswordResetEmail(user.getEmail(), user.getName(), resetLink);
    }

    @Transactional
    public void resetPassword(ResetPasswordDTO dto) {
        String tokenHash = TokenUtil.hashToken(dto.getToken());

        PasswordResetToken resetToken = tokenRepository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new BusinessException("Token de recuperacao invalido ou ja utilizado."));

        if (resetToken.isUsed()) {
            throw new BusinessException("Este link de recuperacao ja foi utilizado.");
        }

        if (resetToken.isExpired()) {
            throw new BusinessException("Este link de recuperacao expirou. Solicite uma nova recuperacao de senha.");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);

        resetToken.setUsed(true);
        tokenRepository.save(resetToken);

        log.info("Senha redefinida com sucesso para o usuario id={}", user.getId());
    }
}
