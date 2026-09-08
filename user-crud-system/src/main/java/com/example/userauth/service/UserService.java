package com.example.userauth.service;

import com.example.userauth.dto.UserRegisterDTO;
import com.example.userauth.dto.UserResponseDTO;
import com.example.userauth.dto.UserUpdateDTO;
import com.example.userauth.entity.User;
import com.example.userauth.exception.BusinessException;
import com.example.userauth.exception.ResourceNotFoundException;
import com.example.userauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Regras de negocio do CRUD de usuarios. */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /** Cadastra um novo usuario, com a senha ja em hash. */
    @Transactional
    public UserResponseDTO register(UserRegisterDTO dto) {
        String normalizedEmail = dto.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new BusinessException("Ja existe um usuario cadastrado com este e-mail.");
        }

        User user = User.builder()
                .name(dto.getName().trim())
                .email(normalizedEmail)
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        User saved = userRepository.save(user);
        return toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id) {
        return toResponseDTO(findEntityById(id));
    }

    /** Usado internamente por outros services (ex.: login) que precisam da entidade completa. */
    @Transactional(readOnly = true)
    public User findEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado (id=" + id + ")."));
    }

    @Transactional(readOnly = true)
    public User findEntityByEmail(String email) {
        return userRepository.findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado para o e-mail informado."));
    }

    @Transactional
    public UserResponseDTO update(Long id, UserUpdateDTO dto) {
        User user = findEntityById(id);
        String normalizedEmail = dto.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmailAndIdNot(normalizedEmail, id)) {
            throw new BusinessException("Ja existe outro usuario cadastrado com este e-mail.");
        }

        user.setName(dto.getName().trim());
        user.setEmail(normalizedEmail);

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        User saved = userRepository.save(user);
        return toResponseDTO(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario nao encontrado (id=" + id + ").");
        }
        userRepository.deleteById(id);
    }

    private UserResponseDTO toResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
