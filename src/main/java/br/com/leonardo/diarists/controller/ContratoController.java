package br.com.leonardo.diarists.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.leonardo.diarists.model.Contrato;
import br.com.leonardo.diarists.service.ContratoService;

@RestController
@RequestMapping("/contrato")
@CrossOrigin("*")
public class ContratoController {
	
	@Autowired
	private ContratoService contratoService;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public void criar(@RequestBody Contrato contrato) {
		
		contratoService.criar(contrato);
		
	}
	
}
