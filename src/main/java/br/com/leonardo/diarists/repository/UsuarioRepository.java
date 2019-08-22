package br.com.leonardo.diarists.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.leonardo.diarists.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
