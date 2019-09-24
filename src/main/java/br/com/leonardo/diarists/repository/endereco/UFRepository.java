package br.com.leonardo.diarists.repository.endereco;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.leonardo.diarists.model.endereco.UF;

public interface UFRepository extends JpaRepository<UF, Long>{
	
	Optional<UF> findBySigla(String sigla);
	
}
