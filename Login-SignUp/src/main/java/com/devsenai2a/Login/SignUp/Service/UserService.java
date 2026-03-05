package com.devsenai2a.Login.SignUp.Service;

import com.devsenai2a.Login.SignUp.Model.User;
import com.devsenai2a.Login.SignUp.Repository.UserRepository;
import org.springframework.http.codec.AbstractJacksonEncoder;
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

        User usuario = userRepository.findByEmail(email).orElse(null);

        if (usuario == null) {
            return null;
        }

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            return null;
        }

        return usuario;
    }



}
