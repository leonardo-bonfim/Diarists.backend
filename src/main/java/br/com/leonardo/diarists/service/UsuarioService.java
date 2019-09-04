package br.com.leonardo.diarists.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import br.com.leonardo.diarists.model.Usuario;
import br.com.leonardo.diarists.repository.UsuarioRepository;
import br.com.leonardo.diarists.response.Response;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Response<Usuario> salvar(Usuario usuario, BindingResult result) {
		
		var response = new Response<Usuario>();
		
		if(usuarioRepository.findByCpf(usuario.getCpf()).isEmpty()) {
			if(usuarioRepository.findByEmail(usuario.getEmail()).isEmpty()) {
				if(!result.hasErrors()) {
					var passwordEncoder = new BCryptPasswordEncoder();
					
					usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
					System.out.println(usuario.getFoto());
					usuarioRepository.save(usuario);
					return response;
				}
				else result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			}
			else response.getErrors().add("Este email já está sendo usado!");
		}
		else response.getErrors().add("Este cpf já está sendo usado!");
		
		return response;
		
	}
	
}
