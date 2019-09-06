package br.com.leonardo.diarists.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.leonardo.diarists.dto.ContratoDto;
import br.com.leonardo.diarists.model.Contrato;
import br.com.leonardo.diarists.response.Response;
import br.com.leonardo.diarists.service.ContratoService;

@RestController
@RequestMapping("/contrato")
@CrossOrigin("*")
public class ContratoController {
	
	@Autowired
	private ContratoService contratoService;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Response<Contrato>> criar(
			@Valid @RequestBody Contrato contrato,
			BindingResult result,
			HttpServletRequest request
	) throws JsonParseException, JsonMappingException, IOException {
		
		String email = obterEmailDoJwt(request);
		var response = contratoService.criar(contrato, result, email);
		
		if(response.getErrors().isEmpty())
			return ResponseEntity.created(null).build();
		
		return ResponseEntity.badRequest().body(response);
		
	}
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public Response<Page<ContratoDto>> listarContratosDoUsuario(
			Pageable pageable,
			HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		
		String email = obterEmailDoJwt(request);
		
		var response = new Response<Page<ContratoDto>>();
		response.setData(contratoService.buscarContratosDoUsuario(email, pageable));
		
		return response;
	}

	@GetMapping("/proximos")
	@ResponseStatus(code = HttpStatus.OK)
	public Response<Page<ContratoDto>> buscarContratosProximos(
		@RequestParam String latitude,
		@RequestParam String longitude,
		@RequestParam Double range,
		Pageable pageable) {
		
		var response = new Response<Page<ContratoDto>>();
		response.setData(contratoService.buscarContratosProximos(latitude, longitude, range, pageable));
		
		return response;
		
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Response<Contrato>> adicionarUsuarioNoContrato(
		@PathVariable("id") Long contratoId,
		HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		
		String email = obterEmailDoJwt(request);
		
		Response<Contrato> response = contratoService.adicionarNoContrato(email, contratoId);
		
		if(response.getErrors().isEmpty())
			return ResponseEntity.ok().build();
		
		return ResponseEntity.badRequest().body(response);
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
