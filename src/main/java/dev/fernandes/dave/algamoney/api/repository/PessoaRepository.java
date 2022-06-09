package dev.fernandes.dave.algamoney.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import dev.fernandes.dave.algamoney.api.model.Pessoa;

public interface PessoaRepository  extends JpaRepository<Pessoa, Integer>{
	
    public Page<Pessoa> findByNomeContainingOrderByIdDesc(String nome, Pageable pageable);

}
