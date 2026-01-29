package com.ctdevsenai2a.ConversorIdade;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class APIconversoridadeController {
    @PostMapping("/Idade")
    @ResponseBody
    public Map<String, Object> IdadeJson(
            @RequestParam double idade,
            @RequestParam String tipoIdade,
            @RequestParam String tipoCon){

        double resultado = 0;
        if (idade > 120 || idade < 0) {
            System.out.println("Insira uma idade valida");
        } else {
            switch (tipoIdade + tipoCon){
                case "anos" + "dias": resultado = idade * 365; break;
                case "anos" + "meses": resultado = idade * 12; break;
                case "dias" + "anos": resultado = idade / 365; break;
                case "dias" + "meses": resultado = idade / 30; break;
                case "meses" + "anos": resultado = idade / 12; break;
                case "meses" + "dias": resultado = idade * 30; break;
            }
        }
        Map<String, Object> resp = new HashMap<>();
        resp.put("resultado", resultado);
        resp.put("errou", null);

        return resp;
    }
}

