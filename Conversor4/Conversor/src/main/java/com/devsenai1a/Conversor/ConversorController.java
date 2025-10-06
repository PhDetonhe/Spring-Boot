package com.devsenai1a.Conversor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConversorController {
	@PostMapping("/conversor")
	@ResponseBody
	public Map<String , Object> conversorJson(
			@RequestParam double temp,
			@RequestParam String de,
			@RequestParam String para){
		
		double resultado = 0;
		String erro = null;
		
		
		switch(de + para) {
		case "Celsius" +  "Kelvin": resultado = temp + 273.15; break;
		case "Celsius"  + "Fahrenheit": resultado = (temp * 1.8) + 32; break;
		case "Kelvin" + "Celsius": resultado = temp - 273.15 ; break;
		case "Kelvin" + "Fahrenheit": resultado = ((temp - 273.15) * 1.8) + 32 ; break;
		case "Fahrenheit" + "Celsius": resultado = (temp - 32) / 18 ; break;
		case "Fahrenheit"+ "Kelvin": resultado = ((temp - 32) / 1.8) + 273.15 ; break;
		default: erro = "unidade invalida";	
			}
		
		
		Map<String, Object> resp = new HashMap<>();
		resp.put("resultado", resultado);
		resp.put("errou", erro);
		return resp;
	}
	
}

