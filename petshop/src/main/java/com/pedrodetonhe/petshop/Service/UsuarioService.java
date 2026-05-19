package com.pedrodetonhe.petshop.Service;

import com.pedrodetonhe.petshop.DTO.AuthResponse;
import com.pedrodetonhe.petshop.DTO.LoginRequest;
import com.pedrodetonhe.petshop.DTO.RegisterRequest;
import com.pedrodetonhe.petshop.Model.Usuario;
import com.pedrodetonhe.petshop.Repository.UsuarioRepository;
import com.pedrodetonhe.petshop.Security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioService(UsuarioRepository repository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ==================== CADASTRO (USER) ====================
    public AuthResponse cadastrar(RegisterRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado!");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));

        // FIX: cadastro público sempre cria USER, independente do que vier no request
        usuario.setRole(Usuario.Role.USER);

        // FIX: captura o retorno do save para garantir que o objeto persistido (com ID) seja usado
        usuario = repository.save(usuario);

        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRole().name());
        return new AuthResponse(token, usuario.getNome(), usuario.getEmail(), usuario.getRole().name());
    }

    // ==================== CADASTRO ADMIN (apenas ADMIN pode chamar) ====================
    public AuthResponse cadastrarAdmin(RegisterRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado!");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setRole(Usuario.Role.ADMIN);

        // FIX: captura o retorno do save
        usuario = repository.save(usuario);

        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRole().name());
        return new AuthResponse(token, usuario.getNome(), usuario.getEmail(), usuario.getRole().name());
    }

    // ==================== LOGIN ====================
    public AuthResponse login(LoginRequest request) {
        Usuario usuario = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta!");
        }

        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRole().name());
        return new AuthResponse(token, usuario.getNome(), usuario.getEmail(), usuario.getRole().name());
    }

    // ==================== LISTAR TODOS (ADMIN) ====================
    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    // ==================== BUSCAR POR ID ====================
    public Usuario buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    // ==================== ATUALIZAR ====================
    public Usuario atualizar(Integer id, RegisterRequest request) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());

        if (request.getSenha() != null && !request.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        }

        if (request.getRole() != null) {
            usuario.setRole(request.getRole());
        }

        return repository.save(usuario);
    }

    // ==================== DELETAR (ADMIN) ====================
    public void deletar(Integer id) {
        repository.deleteById(id);
    }
}