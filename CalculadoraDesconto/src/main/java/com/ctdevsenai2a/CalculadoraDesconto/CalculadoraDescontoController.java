package com.ctdevsenai2a.CalculadoraDesconto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CalculadoraDescontoController {

    @PostMapping("/Desconto")
    @ResponseBody
    public Map<String, Object> calcularDesconto(
            @RequestParam String nome,
            @RequestParam double preco,
            @RequestParam double desconto) {

        
        Map<String, Object> resp = new HashMap<>();
        
       
        if (preco < 0 || desconto < 0 || desconto > 50) {
            resp.put("errou", "Insira um valor de preço válido e o desconto deve ser entre 0 e 50%");
            resp.put("resultado", null);
            return resp;
        }

        
        double descontoPorcentagem = preco * desconto / 100;
        double valorFinal = preco - descontoPorcentagem;
        
        
        String resultado;
        
        if (desconto > 30) {
            resultado = "Valor original: R$ " + String.format("%.2f", preco) + "\n" +
                        "Desconto: R$ " + String.format("%.2f", descontoPorcentagem) + "\n" +
                        "Parabéns, você recebeu um desconto especial!\n" +
                        "Valor final: R$ " + String.format("%.2f", valorFinal);
        } else {
            resultado = "Valor original: R$ " + String.format("%.2f", preco) + "\n" +
                        "Desconto: R$ " + String.format("%.2f", descontoPorcentagem) + "\n" +
                        "Valor final: R$ " + String.format("%.2f", valorFinal);
        }

        
        resp.put("resultado", resultado);
        resp.put("errou", null); 

        return resp;
    }
}
