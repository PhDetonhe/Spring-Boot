package com.devsenai1a.ConversorMoedas;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConversorMoedasController {
	@PostMapping("/moedas")
	@ResponseBody
	public Map<String , Object> moedasJson(
			@RequestParam double moeda,
			@RequestParam String de,
			@RequestParam String para){
		
		double resultado = 0;
		String erro = null;
		
		switch(de + para) {
		case "Real" + "Euro": resultado = moeda / 6.2; break;
		case "Real" + "Dolar": resultado = moeda / 5.3; break; 
		case "Euro" + "Real": resultado = moeda * 6.2;break;
		case "Euro" + "Dolar": resultado = moeda * 1.16; break;
		case "Dolar" + "Euro": resultado = moeda / 1.16; break;
		case "Dolar" + "Real": resultado =  moeda * 5.3; break;
		default: erro = "moeda invalida";
		}
		
		
		Map<String, Object> resp = new HashMap<>();
		resp.put("resultado", resultado);
		resp.put("erro", erro);
		return resp;

	}
}
