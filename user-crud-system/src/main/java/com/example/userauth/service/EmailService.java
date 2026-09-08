package com.example.userauth.service;

import com.example.userauth.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Servico responsavel por enviar e-mails transacionais usando a API HTTP
 * do Resend (https://resend.com/docs/api-reference/emails/send-email).
 * <p>
 * Nao ha SDK oficial obrigatorio: um simples POST autenticado por Bearer
 * token para https://api.resend.com/emails e suficiente.
 */
@Slf4j
@Service
public class EmailService {

    private final RestTemplate restTemplate;

    @Value("${resend.api.key}")
    private String resendApiKey;

    @Value("${resend.api.url}")
    private String resendApiUrl;

    @Value("${resend.from.email}")
    private String fromEmail;

    public EmailService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /** Envia o e-mail de recuperacao de senha contendo o link com o token. */
    public void sendPasswordResetEmail(String toEmail, String userName, String resetLink) {
        String subject = "Recuperacao de senha";
        String html = buildResetPasswordHtml(userName, resetLink);
        send(toEmail, subject, html);
    }

    private void send(String toEmail, String subject, String htmlContent) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(resendApiKey);

        Map<String, Object> body = Map.of(
                "from", fromEmail,
                "to", List.of(toEmail),
                "subject", subject,
                "html", htmlContent
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            restTemplate.postForEntity(resendApiUrl, request, String.class);
            log.info("E-mail de recuperacao de senha enviado para {}", toEmail);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            HttpStatusCode status = ex.getStatusCode();
            log.error("Falha ao enviar e-mail via Resend. Status={} Body={}", status, ex.getResponseBodyAsString());
            throw new BusinessException(
                    "Nao foi possivel enviar o e-mail de recuperacao de senha no momento. Tente novamente mais tarde.");
        } catch (Exception ex) {
            log.error("Erro inesperado ao chamar a API do Resend", ex);
            throw new BusinessException(
                    "Nao foi possivel enviar o e-mail de recuperacao de senha no momento. Tente novamente mais tarde.");
        }
    }

    private String buildResetPasswordHtml(String userName, String resetLink) {
        return """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <body style="font-family: Arial, Helvetica, sans-serif; background:#f4f5f7; padding:24px;">
                  <table role="presentation" width="100%%" style="max-width:480px;margin:0 auto;background:#ffffff;border-radius:8px;overflow:hidden;">
                    <tr>
                      <td style="background:#4f46e5;padding:24px;text-align:center;">
                        <h1 style="color:#ffffff;margin:0;font-size:20px;">Recuperacao de senha</h1>
                      </td>
                    </tr>
                    <tr>
                      <td style="padding:24px;color:#1f2937;">
                        <p>Ola, %s.</p>
                        <p>Recebemos uma solicitacao para redefinir sua senha. Clique no botao abaixo para criar uma nova senha. Este link e valido por tempo limitado.</p>
                        <p style="text-align:center;margin:32px 0;">
                          <a href="%s" style="background:#4f46e5;color:#ffffff;text-decoration:none;padding:12px 24px;border-radius:6px;font-weight:bold;display:inline-block;">
                            Redefinir minha senha
                          </a>
                        </p>
                        <p>Se voce nao solicitou essa alteracao, apenas ignore este e-mail - sua senha atual continuara valida.</p>
                        <p style="color:#6b7280;font-size:12px;">Se o botao nao funcionar, copie e cole este link no navegador:<br>%s</p>
                      </td>
                    </tr>
                  </table>
                </body>
                </html>
                """.formatted(userName, resetLink, resetLink);
    }
}
