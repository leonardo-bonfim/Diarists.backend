package br.com.leonardo.diarists.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.BeanUtils;

import br.com.leonardo.diarists.model.Usuario;
import br.com.leonardo.diarists.repository.UsuarioRepository;
import br.com.leonardo.diarists.response.Response;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Response<Usuario> salvar(Usuario usuario, BindingResult result) {
		
		var response = new Response<Usuario>();
		
		if(usuarioRepository.findByCpf(usuario.getCpf()).isPresent())
			response.getErrors().add("Este cpf já está sendo usado!");
		
		if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent())
			response.getErrors().add("Este email já está sendo usado!");
		
		if(result.hasErrors())
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
		
		if(response.getErrors().isEmpty()) {
			var passwordEncoder = new BCryptPasswordEncoder();
			
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			usuarioRepository.save(usuario);
			return response;
		}
		
		return response;
		
	}
	
	public Response<Usuario> atualizar(String email, Usuario usuario, BindingResult result) {
		Usuario usuarioSalvo = buscarUsuarioExistente(email);
		
		var response = this.atualizarUsuario(usuario, usuarioSalvo);
		
		if (response.getErrors().isEmpty())
			usuarioRepository.save(response.getData());

		return response;
	}
	
	private Response<Usuario> atualizarUsuario(Usuario usuario, Usuario usuarioSalvo) {
		
		var response = new Response<Usuario>();
		
		if (usuario.getNome() != null)
			usuarioSalvo.setNome(usuario.getNome());

		if (usuario.getSobrenome() != null)
			usuarioSalvo.setSobrenome(usuario.getNome());
		
		if (usuario.getEmail() != null) {
			
			if (usuario.getEmail().equals(usuarioSalvo.getEmail()))
				response.getErrors().add("Email a ser modificado é igual ao atual!");
			else
				if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent())
					response.getErrors().add("Este email já está sendo usado!");
			
			usuarioSalvo.setEmail(usuario.getEmail());
		}
		
		if (usuario.getCpf() != null) {
			if(usuario.getCpf().equals(usuarioSalvo.getCpf())) {
				response.getErrors().add("Cpf a ser modificado é igual ao atual!");
			}
			else
				if(usuarioRepository.findByCpf(usuario.getCpf()).isPresent())
					response.getErrors().add("Este cpf já está sendo usado!");
			
			usuarioSalvo.setCpf(usuario.getCpf());
		}
		
		if (usuario.getSenha() != null) {
			var passwordEncoder = new BCryptPasswordEncoder();
			usuarioSalvo.setSenha(passwordEncoder.encode(usuario.getSenha()));
		}
		
		if (usuario.getEndereco() != null)
			usuarioSalvo.setEndereco(usuario.getEndereco());
		
		if (usuario.getSexo() != null)
			usuarioSalvo.setSexo(usuario.getSexo());
		
		if (usuario.getFoto() != null)
			usuarioSalvo.setFoto(usuario.getFoto());
		
		if (response.getErrors().isEmpty())
			response.setData(usuarioSalvo);

		return response;
	}
	
	private Usuario buscarUsuarioExistente(String email) {
		Optional<Usuario> usuarioSalvo = usuarioRepository.findByEmail(email);
		if (usuarioSalvo.isEmpty()) {
			throw new IllegalArgumentException();
		}
		return usuarioSalvo.get();
	}
	
}
