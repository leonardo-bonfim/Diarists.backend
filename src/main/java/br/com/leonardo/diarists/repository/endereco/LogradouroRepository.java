package br.com.leonardo.diarists.repository.endereco;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.leonardo.diarists.model.endereco.Logradouro;

public interface LogradouroRepository extends JpaRepository<Logradouro, Long>{
	Optional<Logradouro> findByNome(String nome);
}
