package br.com.leonardo.diarists.repository.endereco;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.leonardo.diarists.model.endereco.Bairro;

public interface BairroRepository extends JpaRepository<Bairro, Long>{
	Optional<Bairro> findByNome(String nome);
}