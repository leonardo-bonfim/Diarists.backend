package br.com.leonardo.diarists.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.leonardo.diarists.model.Contrato;
import br.com.leonardo.diarists.repository.ContratoRepository;

@Service
public class ContratoService {
	
	@Autowired
	private ContratoRepository contratoRepository;
	
	public void criar(Contrato contrato) {
		
		//var usuario = usuarioRepository.findById(1L); TODO Implementar pegar do usuário quando for mexer com login
		
		//usuario.get().getContratos().add(contrato);
		
		contratoRepository.save(contrato);
		//usuarioRepository.save(usuario.get());
		
	}
	
	public Page<Contrato> buscarContratosProximos(String latitude, String longitude, Double range, Pageable pageable) {
		return contratoRepository.findContratosByRange(latitude, longitude, range, pageable);
	}
	
}
