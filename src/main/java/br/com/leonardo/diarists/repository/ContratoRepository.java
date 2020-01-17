package br.com.leonardo.diarists.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.leonardo.diarists.model.Contrato;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
	
	static final String HAVERSINE_PART = "(6371 * acos(cos(radians(:latitude)) * cos(radians(m.latitude)) * cos(radians(m.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(m.latitude))))";
	
	@Query("SELECT m FROM Contrato m JOIN m.usuarios usuario WHERE " + HAVERSINE_PART + " < :range and SIZE(m.usuarios) = 1 and usuario.id <> :usuario_id and (m.restricao = :restricao or m.restricao = 'N')")
	List<Contrato> findContratosByRange(
			@Param("latitude") final String latitude,
			@Param("longitude") final String longitude,
			@Param("range") final Double distance,
			@Param("restricao") final String restricao,
			@Param("usuario_id") final Long id,
			Pageable pageable);
	
}
