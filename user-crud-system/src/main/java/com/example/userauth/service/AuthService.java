package com.example.userauth.service;

import com.example.userauth.dto.LoginDTO;
import com.example.userauth.dto.UserResponseDTO;
import com.example.userauth.entity.User;
import com.example.userauth.exception.BusinessException;
import com.example.userauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Regras de negocio de autenticacao (login). */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Valida e-mail e senha informados contra o hash armazenado no banco.
     * Lanca BusinessException com mensagem generica caso as credenciais
     * sejam invalidas, evitando revelar se o e-mail existe ou nao.
     */
    @Transactional(readOnly = true)
    public User authenticate(LoginDTO dto) {
        String normalizedEmail = dto.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new BusinessException("E-mail ou senha invalidos."));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException("E-mail ou senha invalidos.");
        }

        return user;
    }

    public UserResponseDTO toResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
