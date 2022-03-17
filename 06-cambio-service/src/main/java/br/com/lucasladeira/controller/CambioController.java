package br.com.lucasladeira.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasladeira.entities.Cambio;
import br.com.lucasladeira.repositories.CambioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cambio endpoint") //swagger
@RestController
@RequestMapping("/cambio-service")
public class CambioController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private CambioRepository cambioRepository;
	
	
	@Operation(summary = "Get cambio from currency") //swagger
	@GetMapping(value = "/{amount}/{from}/{to}")
	public Cambio getCambio(
			@PathVariable("amount") BigDecimal amount,
			@PathVariable("from") String from,
			@PathVariable("to") String to
			){
		
		var cambio = cambioRepository.findByFromAndTo(from, to);
		
		if (cambio == null) {
			throw new RuntimeException("Currency Unsupported");
		}
		
		var port = environment.getProperty("local.server.port"); //recuperando porta da aplicacao
		BigDecimal conversionFactor = cambio.getConversionFactor();
		BigDecimal convertedValue = conversionFactor.multiply(amount);
		cambio.setEnvironment(port);
									//arredondando para duas casas decimais
		cambio.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));
		
		return cambio;
	}
}
