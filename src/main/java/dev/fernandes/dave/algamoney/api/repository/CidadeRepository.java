package dev.fernandes.dave.algamoney.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.fernandes.dave.algamoney.api.model.Cidade;

public interface CidadeRepository  extends JpaRepository<Cidade, Integer>{
	
	List<Cidade> findByEstadoId(int idEstado);

}
