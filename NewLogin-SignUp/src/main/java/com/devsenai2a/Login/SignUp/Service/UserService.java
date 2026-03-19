package com.devsenai2a.Login.SignUp.Service;

import com.devsenai2a.Login.SignUp.Model.User;
import com.devsenai2a.Login.SignUp.Repository.UserRepository;
import org.springframework.http.codec.AbstractJacksonEncoder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //LISTAR
    public List<User> getAll(){
        return userRepository.findAll();
    }

    //listar por id
    public User findById(Integer id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User não encontrado"));
    }
    //CRIAR/CRIPTOGRAFIA
    public User save(User user) {
        user.setSenha(passwordEncoder.encode(user.getSenha()));
        return userRepository.save(user);
    }

    //DELETAR
    public void delete(Integer id){
        userRepository.deleteById(id);
    }

    //UPDATE
    public User update(Integer id, User user) {

        User usuarioExistente = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User não encontrado"));

        if(user.getSenha() != null && !user.getSenha().isEmpty()){
            usuarioExistente.setSenha(passwordEncoder.encode(user.getSenha()));
        }

        usuarioExistente.setName(user.getName());
        usuarioExistente.setEmail(user.getEmail());
        usuarioExistente.setPerfil(user.getPerfil());
        usuarioExistente.setEndereco(user.getEndereco());
        usuarioExistente.setBairro(user.getBairro());
        usuarioExistente.setComplemento(user.getComplemento());
        usuarioExistente.setCep(user.getCep());
        usuarioExistente.setCidade(user.getCidade());
        usuarioExistente.setEstado(user.getEstado());
        usuarioExistente.setFoto(user.getFoto());

        return userRepository.save(usuarioExistente);
    }

    //LOGIN
    public User login(String email, String senha) {

        User usuario = userRepository.findByEmail(email);

        if (usuario == null) {
            return null;
        }

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            return null;
        }

        return usuario;
    }

    private JavaMailSender mailSender; // Injeta o enviador de e-mail

    public boolean enviarEmailRecuperacao(String email) {

        User usuario = userRepository.findByEmail(email);

        if (usuario != null) {

            String link = "http://127.0.0.1:5500/redefinir-senha.html?email=" + email;

            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setTo(email);
            mensagem.setSubject("Recuperação de Senha - DevSenai");
            mensagem.setText(
                    "Olá " + usuario.getName() + ",\n\n" +
                            "Clique no link abaixo para criar uma nova senha:\n" +
                            link
            );

            mailSender.send(mensagem);

            System.out.println("Email enviado para: " + email);

            return true;
        }

        return false;
    }

    //Redefinir Senha
    public boolean redefinirSenha(String email, String novaSenha) {
        User usuario = userRepository.findByEmail(email);

        if (usuario != null) {
            // Aqui você pode aplicar hash na senha antes de salvar
            usuario.setSenha(new BCryptPasswordEncoder().encode(novaSenha));
            userRepository.save(usuario);
            return true;
        }
        return false;
    }




}
