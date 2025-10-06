package com.devsenai1A.calculadora;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculadoraController {

	@GetMapping("/somar")
	public double somar(@RequestParam double a, @RequestParam double b) {
		return a + b;
	}
	
	@GetMapping("/subtrair")
	public double subtrair(@RequestParam double a, @RequestParam double b) {
		return a - b;
	}
	
	@GetMapping("/multiplicar")
	public double dividir(@RequestParam double a, @RequestParam double b) {
		return a / b;
	}
	
	@GetMapping("/dividir")
	public double multiplicar(@RequestParam double a, @RequestParam double b) {
		return a * b;
	}
	
	@PostMapping("/calcular")
	@ResponseBody
	public Map<String , Object> calcularJson(
			@RequestParam double num1,
			@RequestParam double num2,
			@RequestParam String operacao){
		
		double resultado = 0;
		String erro = null;
		
		switch(operacao) {
		case "somar": resultado = num1 + num2; break;
		case "subtrair": resultado = num1 - num2; break;
		case "multiplicar": resultado = num1 / num2; break;
		case "dividir":
			if(num2 == 0) erro = "Divisão com 0 é invalida";
			else resultado = num1 / num2; break;
		default: erro = "operação invalida";	
			}
		Map<String, Object> resp = new HashMap<>();
		resp.put("resultado", resultado);
		resp.put("errou", erro);
		return resp;
	}
	
}


