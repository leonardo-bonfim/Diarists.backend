package br.com.leonardo.diarists.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.leonardo.diarists.model.Contrato;

public interface ContratoRepository extends JpaRepository<Contrato, Long>{
	static final String HAVERSINE_PART = "(6371 * acos(cos(radians(:latitude)) * cos(radians(m.latitude)) * cos(radians(m.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(m.latitude))))";
	
	@Query("SELECT m FROM Contrato m WHERE " + HAVERSINE_PART + " < :range")
	List<Contrato> findContratosByRange(@Param("latitude") final String latitude, @Param("longitude") final String longitude, @Param("range") final Double distance, Pageable pageable);
	
}
