package br.com.leonardo.diarists.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.leonardo.diarists.model.Usuario;
import br.com.leonardo.diarists.model.group.Alteracao;
import br.com.leonardo.diarists.model.group.Criacao;
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
	public ResponseEntity<Response<Usuario>> criar(@Validated(Criacao.class) @RequestBody Usuario usuario, BindingResult result) {
		
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
	
	@PutMapping
	public ResponseEntity<Response<Usuario>> modificar(@RequestBody @Validated(Alteracao.class) Usuario usuario,
			HttpServletRequest request, BindingResult result) throws JsonParseException, JsonMappingException, IOException {
		
		String email = this.obterEmailDoJwt(request);
		
		try {
			var response = usuarioService.atualizar(email, usuario, result);
			
			if (response.getErrors().isEmpty())
				return ResponseEntity.ok(response);
			
			return ResponseEntity.badRequest().body(response);
			
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}

	}
	
	private String obterEmailDoJwt(HttpServletRequest request)
			throws IOException, JsonParseException, JsonMappingException {
		
		String token = request.getHeader("Authorization").split(" ")[1];
		
		Jwt jwt = JwtHelper.decode(token);
		String jwtDecoded = jwt.getClaims();
	    Map<?, ?> jwtDecodedMap = new ObjectMapper().readValue(jwtDecoded, HashMap.class);
	    String email = jwtDecodedMap.get("user_name").toString();
		return email;
	}
	
}
