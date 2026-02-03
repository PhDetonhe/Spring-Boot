package com.devsenai2a.AvalliarAluno;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AvaliarAlunoController {
    @PostMapping("/calcular")
    public Map <String, Object>calcularJson(
            @RequestParam double n1,
            @RequestParam double n2,
            @RequestParam double n3,
            @RequestParam double n4,
            @RequestParam String nome
    ){

        String status = "";



        if (n1 < 0 || n2 < 0 || n3 < 0 || n4 < 0) {

            Map<String, Object> error = new HashMap<>();
            error.put("erro", "As notas não podem ser negativas");
            return error;

        } else{

            double media = (n1 + n2 + n3 + n4) / 4;

            if (media > 7){
                status = "aprovado";
            }
            if (media >= 5 && media<= 7){
                status = "recuperacao";
            }
            if (media < 5){
                status = "reprovado";
            }


            Map<String, Object> resp = new LinkedHashMap<>();
            resp.put("Nome do Aluno: ", nome);
            resp.put("Nota 1: ", n1);
            resp.put("Nota 2: ", n2);
            resp.put("Nota 3: ", n3);
            resp.put("Nota 4: ", n4);
            resp.put("Média", media);
            resp.put("Status", status);
            return resp;


        }
    }
}