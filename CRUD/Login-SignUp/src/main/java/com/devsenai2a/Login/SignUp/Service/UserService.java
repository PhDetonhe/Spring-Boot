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
        return userRepository.save(user);
    }


}
