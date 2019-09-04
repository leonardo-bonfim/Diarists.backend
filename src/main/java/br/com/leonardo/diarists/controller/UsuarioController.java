package br.com.leonardo.diarists.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.leonardo.diarists.model.Usuario;
import br.com.leonardo.diarists.repository.UsuarioRepository;
import br.com.leonardo.diarists.response.Response;
import br.com.leonardo.diarists.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
@CrossOrigin("*")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity<Response<Usuario>> criar(@Valid @RequestBody Usuario usuario, BindingResult result) {
		
		var response = usuarioService.salvar(usuario, result);
		
		if(response.getErrors().isEmpty())
			return ResponseEntity.created(null).build();
		
		return ResponseEntity.badRequest().body(response);
	}
	
	@GetMapping("/foto")
	public ResponseEntity<String> obterFoto(@RequestParam String email) {
		
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
		String fotoBs64 = Base64.encodeBase64String(usuario.get().getFoto());
		
		return ResponseEntity.ok(fotoBs64);
		
	}
	
}
