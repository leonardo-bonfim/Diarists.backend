package br.com.leonardo.diarists.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import br.com.leonardo.diarists.model.Usuario;
import br.com.leonardo.diarists.model.endereco.Bairro;
import br.com.leonardo.diarists.model.endereco.Cidade;
import br.com.leonardo.diarists.model.endereco.Endereco;
import br.com.leonardo.diarists.model.endereco.Logradouro;
import br.com.leonardo.diarists.model.endereco.UF;
import br.com.leonardo.diarists.repository.UsuarioRepository;
import br.com.leonardo.diarists.repository.endereco.BairroRepository;
import br.com.leonardo.diarists.repository.endereco.CidadeRepository;
import br.com.leonardo.diarists.repository.endereco.EnderecoRepository;
import br.com.leonardo.diarists.repository.endereco.LogradouroRepository;
import br.com.leonardo.diarists.repository.endereco.UFRepository;
import br.com.leonardo.diarists.response.Response;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private UFRepository ufRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private BairroRepository bairroRepository;
	@Autowired
	private LogradouroRepository logradouroRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	
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
			
			Endereco endereco = gerenciarEndereco(usuario.getEndereco());
			
			usuario.setEndereco(endereco);
			
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			
			usuarioRepository.save(usuario);
			return response;
		}
		
		return response;
		
	}

	private Endereco gerenciarEndereco(Endereco endereco) {
		
		Endereco novoEndereco = new Endereco();
		
		Optional<Endereco> enderecoOpt = enderecoRepository.findByCep(endereco.getCep());
		
		if(enderecoOpt.isPresent()) {
			if(endereco.getLogradouro().getNumero().equals(enderecoOpt.get().getLogradouro().getNumero())) {
				novoEndereco = enderecoOpt.get();
			}
			else {
				Logradouro logradouro = new Logradouro();
				logradouro.setNome(enderecoOpt.get().getLogradouro().getNome());
				logradouro.setBairro(enderecoOpt.get().getLogradouro().getBairro());
				logradouro.setNumero(endereco.getLogradouro().getNumero());
				
				System.err.println(logradouro.getId());
				
				novoEndereco.setLogradouro(logradouro);
				
			}
		}
		else {
			
			Optional<Logradouro> logradouroOpt = logradouroRepository.findByNome(endereco.getLogradouro().getNome());
			Optional<Bairro> bairroOpt = bairroRepository.findByNome(endereco.getLogradouro().getBairro().getNome());
			Optional<Cidade> cidadeOpt = cidadeRepository.findByNome(endereco.getLogradouro().getBairro().getCidade().getNome());
			Optional<UF> ufOpt = ufRepository.findBySigla(endereco.getLogradouro().getBairro().getCidade().getUf().getSigla());
			
			novoEndereco.setCep(endereco.getCep());
			
			if(logradouroOpt.isPresent()) {
				if(logradouroOpt.get().getNumero().equals(endereco.getLogradouro().getNumero())) {
					novoEndereco.setLogradouro(logradouroOpt.get());
				}
				else {
					logradouroOpt.get().setNumero(endereco.getLogradouro().getNumero());
					novoEndereco.setLogradouro(logradouroOpt.get());
				}
			}
			else {
				novoEndereco.setLogradouro(endereco.getLogradouro());
			}
			
			if(bairroOpt.isPresent()) {
				novoEndereco.getLogradouro().setBairro(bairroOpt.get());
			}
			else {
				novoEndereco.getLogradouro().setBairro(endereco.getLogradouro().getBairro());
			}
			
			if(cidadeOpt.isPresent()) {
				novoEndereco.getLogradouro().getBairro().setCidade(cidadeOpt.get());
			}
			else {
				novoEndereco.getLogradouro().getBairro().setCidade(endereco.getLogradouro().getBairro().getCidade());
			}
			
			if(ufOpt.isPresent()) {
				novoEndereco.getLogradouro().getBairro().getCidade().setUf(ufOpt.get());
			}
			else {
				novoEndereco.getLogradouro().getBairro().getCidade().setUf(endereco.getLogradouro().getBairro().getCidade().getUf());
			}
		}
		
		return novoEndereco;
	}
	
}
