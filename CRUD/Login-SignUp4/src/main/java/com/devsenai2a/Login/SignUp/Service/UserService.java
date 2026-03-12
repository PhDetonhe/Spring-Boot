package com.devsenai2a.Login.SignUp.Service;

import com.devsenai2a.Login.SignUp.Model.User;
import com.devsenai2a.Login.SignUp.Repository.UserRepository;
import org.jspecify.annotations.Nullable;
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
    private final JavaMailSender mailSender;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JavaMailSender mailSender) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
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

    //Recuperar senha
    public boolean emailRecuperacao(String email){

        User user = userRepository.findByEmail(email);


        if (user != null) {
            String link = "http://127.0.0.1:5500/Passsword.html?email=" + email;

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Recuperação de senha");
            message.setText("Olá" + user.getName() + ",\n\n" + "Clique no link abaixo para alterar sua senha" + link);

            mailSender.send(message);

            System.out.println("O E-mail foi enviado para: " + email);

            return true;
        }

        return false;
    }

    public boolean redefinirSenha(String email, String Novasenha){
        User user = userRepository.findByEmail(email);

        if (user != null) {
            user.setSenha(new BCryptPasswordEncoder().encode(Novasenha));
            userRepository.save(user);
            return true;
        }
        return false;
    }



}
