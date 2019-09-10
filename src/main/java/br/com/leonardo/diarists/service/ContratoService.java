package br.com.leonardo.diarists.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import br.com.leonardo.diarists.dto.ContratoDto;
import br.com.leonardo.diarists.dto.UsuarioDto;
import br.com.leonardo.diarists.model.Contrato;
import br.com.leonardo.diarists.model.Usuario;
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
	
	public Page<ContratoDto> buscarContratosDoUsuario(String email, Pageable pageable) {
		var usuario = usuarioRepository.findByEmail(email);
		var contratoList = usuario.get().getContratos();
		
		List<ContratoDto> contratoDtoList = paginarContratos(contratoList, pageable);
		
		return new PageImpl<>(contratoDtoList, pageable, contratoList.size());
	}
	

	public Response<Contrato> adicionarNoContrato(String email, Long contratoId) {
		
		var response = new Response<Contrato>();
		var contrato = contratoRepository.findById(contratoId);
		var usuario = usuarioRepository.findByEmail(email);
		
		if(!usuario.isPresent()) {
			response.getErrors().add("Usuário não existe!");
			return response;
		}
			
		if(!contrato.isPresent()) {
			response.getErrors().add("Contrato não existe!");
			return response;
		}
		
		if(contrato.get().getUsuarios().size() >= 2)
			response.getErrors().add("Este contrato já está fechado!");
		
		if(contrato.get().getUsuarios().indexOf(usuario.get()) != -1)
			response.getErrors().add("Este usuário já está alocado neste contrato!");
		
		if(response.getErrors().size() == 0) {
			contrato.get().getUsuarios().add(usuario.get());
			usuario.get().getContratos().add(contrato.get());
			contratoRepository.save(contrato.get());
			usuarioRepository.save(usuario.get());
		}
		
		return response;
	}
	
	public Page<ContratoDto> buscarContratosProximos(String latitude, String longitude, Double range, String email, Pageable pageable) {
		
		var usuario = usuarioRepository.findByEmail(email);
		
		List<Contrato> contratoList = contratoRepository.findContratosByRange(latitude, longitude, range, usuario.get().getSexo(), usuario.get().getId(), pageable);
		List<ContratoDto> contratoDtoList = new ArrayList<>();
		
		contratoList.forEach(contrato -> contratoDtoList.add(contratoToDto(contrato)));
		
		return new PageImpl<>(contratoDtoList, pageable, contratoDtoList.size());
	}
	
	private List<ContratoDto> paginarContratos(List<Contrato> contratoList, Pageable pageable) {
		var contratoDtoList = new ArrayList<ContratoDto>();
		
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		int ultimoRegistroDaPagina = primeiroRegistroDaPagina + totalRegistrosPorPagina;
		
		for(int i = 0; i < contratoList.size(); i++) { //TODO Achar um jeito melhor de fazer esse for
			if(i >= primeiroRegistroDaPagina && i < ultimoRegistroDaPagina) {
				contratoDtoList.add(contratoToDto(contratoList.get(i)));
			}
		}
		return contratoDtoList;
	}
	
	private ContratoDto contratoToDto(Contrato contrato) {
		
		ContratoDto contratoDto = new ContratoDto();
		List<UsuarioDto> usuarios = new ArrayList<>();
		
		contrato.getUsuarios().forEach(usuario -> usuarios.add(usuarioToDto(usuario)));
		
		contratoDto.setUsuarios(usuarios);
		contratoDto.setDescricao(contrato.getDescricao());
		
		return contratoDto;
		
	}
	
	private UsuarioDto usuarioToDto(Usuario usuario) {
		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioDto.setNome(usuario.getNome());
		usuarioDto.setSobrenome(usuario.getSobrenome());
		usuarioDto.setSexo(usuario.getSexo());
		usuarioDto.setEndereco(usuario.getEndereco());
		
		return usuarioDto;
	}
}
