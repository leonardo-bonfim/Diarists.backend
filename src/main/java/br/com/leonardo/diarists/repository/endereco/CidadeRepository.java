package br.com.leonardo.diarists.repository.endereco;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.leonardo.diarists.model.endereco.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long>{
	Optional<Cidade> findByNome(String nome);
}
