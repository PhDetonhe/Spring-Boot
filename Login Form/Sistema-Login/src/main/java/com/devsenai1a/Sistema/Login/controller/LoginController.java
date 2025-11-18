package com.devsenai1a.Sistema.Login.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/authlogin")
public class LoginController {
    private String username = "João Machado";
    private String password= "123456";

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> dados) {
        String User = dados.get("username");
        String Password = dados.get("password");

        if (username.equals(User) && password.equals(Password)) {
            return ResponseEntity.ok("Login Sucessfull");
        }

        return ResponseEntity.status(401).body("Login Failed");
    }
}
