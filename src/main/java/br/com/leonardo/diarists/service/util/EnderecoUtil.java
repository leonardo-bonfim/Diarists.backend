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
	
	public Endereco gerenciarEndereco(Endereco endereco, BindingResult result) {
		
		Logradouro logradouro = endereco.getLogradouro();
		Bairro bairro = null;
		Cidade cidade = null;
		UF uf = null;
		
		if(logradouro != null)
			bairro = logradouro.getBairro();
		
		if(bairro != null)
			cidade = bairro.getCidade();
		
		if(cidade != null)
			uf = cidade.getUf();
		
		if(logradouro == null|| cidade == null 
				|| bairro == null || uf == null)
			return null;
		
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
