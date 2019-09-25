package br.com.leonardo.diarists.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import br.com.leonardo.diarists.model.Usuario;
import br.com.leonardo.diarists.repository.UsuarioRepository;
import br.com.leonardo.diarists.response.Response;
import br.com.leonardo.diarists.service.util.EnderecoUtil;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private EnderecoUtil enderecoUtil;
	
	public Response<Usuario> salvar(Usuario usuario, BindingResult result) {
		
		var response = new Response<Usuario>();
		var endereco = enderecoUtil.gerenciarEndereco(usuario.getEndereco(), result);
		
		if(usuarioRepository.findByCpf(usuario.getCpf()).isPresent())
			response.getErrors().add("Este cpf já está sendo usado!");
		
		if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent())
			response.getErrors().add("Este email já está sendo usado!");
		
		if(endereco == null) {
			response.getErrors().add("Endereço inválido!");
		}
		
		if(result.hasErrors())
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
		
		
		if(response.getErrors().isEmpty()) {
			var passwordEncoder = new BCryptPasswordEncoder();
			
			usuario.setEndereco(endereco);
			
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			
			usuarioRepository.save(usuario);
			return response;
		}
		
		return response;
		
	}

}
