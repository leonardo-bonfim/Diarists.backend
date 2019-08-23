package br.com.leonardo.diarists.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.leonardo.diarists.model.Contrato;
import br.com.leonardo.diarists.model.Usuario;
import br.com.leonardo.diarists.repository.ContratoRepository;
import br.com.leonardo.diarists.repository.UsuarioRepository;

@RestController
@RequestMapping("/contrato")
public class ContratoController {
	
	@Autowired
	private ContratoRepository contratoRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public void criar(@RequestBody Contrato contrato) {
		
		Optional<Usuario> usuario = usuarioRepository.findById(1L);
		
		usuario.get().getContratos().add(contrato);
		
		contratoRepository.save(contrato);
		usuarioRepository.save(usuario.get());
		
	}
	
}
