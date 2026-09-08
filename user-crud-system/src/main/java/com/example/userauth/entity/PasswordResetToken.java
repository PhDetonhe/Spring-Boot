package com.example.userauth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Token de recuperacao de senha.
 * <p>
 * O token enviado por e-mail ao usuario e um valor aleatorio em texto puro,
 * mas SOMENTE o hash (SHA-256) desse valor e persistido no banco. Assim,
 * mesmo que o banco seja comprometido, o token original nao pode ser
 * reconstruido para efetuar um reset de senha indevido.
 */
@Entity
@Table(name = "password_reset_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Hash SHA-256 (hex) do token enviado por e-mail. */
    @Column(name = "token_hash", nullable = false, unique = true, length = 255)
    private String tokenHash;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    @Column(nullable = false)
    private boolean used;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (!this.used) {
            this.used = false;
        }
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiryDate);
    }
}
