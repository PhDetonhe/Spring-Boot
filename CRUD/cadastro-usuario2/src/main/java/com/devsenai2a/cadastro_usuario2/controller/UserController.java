package com.devsenai2a.cadastro_usuario2.controller;

import com.devsenai2a.cadastro_usuario2.model.User;
import com.devsenai2a.cadastro_usuario2.repository.UserRepository;
import com.devsenai2a.cadastro_usuario2.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//camada responsavel por gerenciar as rotas e respostas de requisições HTTP(GET, POST, PUT, DELETE)
@RestController
@RequestMapping("/user") //mapeia as rotas das requisições
public class UserController {
    //injeção de dependencia se trata da conexão de uma camada com a outra, injetando o contexto da camada service no controller por exemplo, o programa é burro pra crlh então ele precisa que a gente oriente esse animal.
    //forma 1:
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //chamamos a função criada no service para pegar todos os usuarios, o list serve jsutamente para listar
    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }
    //GET especifico para o update, permite que o usuario veja todas as informações dele mesmo
    @GetMapping("/{id}")
    public User getById(@PathVariable Integer id){
        return userService.findById(id);
    }

    @PostMapping
    public User create(@RequestBody User user){
        return userService.save(user);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Integer id, @RequestBody User user) {
        user.setId(id); // isso aqui faltava pqp vai tomar no cu coida maldita desgraçada do crlh vai se fuder espero que que m inventou essa porra queime no inferno
        return userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> dados) {

        String email = dados.get("email");
        String senha = dados.get("senha");

        User usuario = userService.login(email, senha);

        if (usuario == null) {
            return ResponseEntity.status(401).body("Email ou senha inválidos");
        }

        return ResponseEntity.ok(usuario);
    }

}
