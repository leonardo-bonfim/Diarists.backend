package br.com.leonardo.diarists.service.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import br.com.leonardo.diarists.model.endereco.Bairro;
import br.com.leonardo.diarists.model.endereco.Cidade;
import br.com.leonardo.diarists.model.endereco.Endereco;
import br.com.leonardo.diarists.model.endereco.Logradouro;
import br.com.leonardo.diarists.model.endereco.UF;
import br.com.leonardo.diarists.repository.endereco.BairroRepository;
import br.com.leonardo.diarists.repository.endereco.CidadeRepository;
import br.com.leonardo.diarists.repository.endereco.EnderecoRepository;
import br.com.leonardo.diarists.repository.endereco.LogradouroRepository;
import br.com.leonardo.diarists.repository.endereco.UFRepository;
import br.com.leonardo.diarists.response.Response;

@Component
public class EnderecoUtil {
	
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
	
	public Response<Endereco> gerenciarEndereco(Endereco endereco, BindingResult result) {
		
		Response<Endereco> response = new Response<>();
		
		Logradouro logradouro = endereco.getLogradouro();
		Bairro bairro = null;
		Cidade cidade = null;
		UF uf = null;
		
		if(logradouro != null)
			bairro = logradouro.getBairro();
		else
			response.getErrors().add("O logradouro não pode ser nulo!");
		
		if(bairro != null)
			cidade = bairro.getCidade();
		else
			response.getErrors().add("O bairro não pode ser nulo!");
		
		if(cidade != null )
			uf = cidade.getUf();
		else
			response.getErrors().add("A cidade não pode ser nula!");
		
		if(uf == null)
			response.getErrors().add("UF não pode ser nulo!");
		
		if(logradouro.getNumero() == null)
			response.getErrors().add("O número do logradouro não pode ser nulo!");
		
		if(logradouro == null|| cidade == null 
				|| bairro == null || uf == null
				|| logradouro.getNumero() == null)
			return response;
		
		Endereco novoEndereco = new Endereco();
		
		Optional<Endereco> enderecoOpt = enderecoRepository.findByCep(endereco.getCep());
		
		if(enderecoOpt.isPresent()) {
			if(logradouro.getNumero().equals(enderecoOpt.get().getLogradouro().getNumero())) {
				novoEndereco = enderecoOpt.get();
			}
			else {
				Logradouro novoLogradouro = new Logradouro();
				novoLogradouro.setNome(enderecoOpt.get().getLogradouro().getNome());
				novoLogradouro.setBairro(enderecoOpt.get().getLogradouro().getBairro());
				novoLogradouro.setNumero(endereco.getLogradouro().getNumero());
				
				novoEndereco.setLogradouro(novoLogradouro);
				
			}
		}
		else {
			
			Optional<Logradouro> logradouroOpt = logradouroRepository.findByNome(logradouro.getNome());
			Optional<Bairro> bairroOpt = bairroRepository.findByNome(bairro.getNome());
			Optional<Cidade> cidadeOpt = cidadeRepository.findByNome(cidade.getNome());
			Optional<UF> ufOpt = ufRepository.findBySigla(uf.getSigla());
			
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
		
		response.setData(novoEndereco);
		return response;
	}

}
