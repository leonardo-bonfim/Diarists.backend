package br.com.leonardo.diarists.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import br.com.leonardo.diarists.model.Contrato;
import br.com.leonardo.diarists.repository.ContratoRepository;
import br.com.leonardo.diarists.repository.UsuarioRepository;
import br.com.leonardo.diarists.response.Response;

@Service
public class ContratoService {
	
	@Autowired
	private ContratoRepository contratoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Response<Contrato> criar(Contrato contrato, BindingResult result, String email) {
		
		var response = new Response<Contrato>();
		
		var usuario = usuarioRepository.findByEmail(email);
		if(usuario.isPresent()) {
			if(!result.hasErrors()) {
				usuario.get().getContratos().add(contrato);
				contratoRepository.save(contrato);
				usuarioRepository.save(usuario.get());
			}
			else result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
		}
		else response.getErrors().add("Usuário não existe!");
		
		return response;
	}
	
	public Response<Contrato> adicionarNoContrato(String email, Long contratoId) {
		
		var response = new Response<Contrato>();
		
		var contrato = contratoRepository.findById(contratoId);
		var usuario = usuarioRepository.findByEmail(email);
		
		if(usuario.isPresent()) {
			if(contrato.isPresent()) {
				if(contrato.get().getUsuarios().size() < 2) {
					contrato.get().getUsuarios().add(usuario.get());
					usuario.get().getContratos().add(contrato.get());
					contratoRepository.save(contrato.get());
					usuarioRepository.save(usuario.get());
				}
				else response.getErrors().add("Este contrato já está fechado!");
			}
			else response.getErrors().add("Contrato não existe!");
		}
		else response.getErrors().add("Usuário não existe!");
		
		return response;
	}
	
	public Page<Contrato> buscarContratosProximos(String latitude, String longitude, Double range, Pageable pageable) {
		return contratoRepository.findContratosByRange(latitude, longitude, range, pageable);
	}
	
}
