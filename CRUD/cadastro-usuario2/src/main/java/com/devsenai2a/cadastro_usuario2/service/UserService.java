package com.devsenai2a.cadastro_usuario2.service;

import com.devsenai2a.cadastro_usuario2.model.User;
import com.devsenai2a.cadastro_usuario2.repository.UserRepository;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

//LOGIN

}





