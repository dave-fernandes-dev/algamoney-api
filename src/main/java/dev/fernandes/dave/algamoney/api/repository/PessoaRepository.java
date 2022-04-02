package dev.fernandes.dave.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.fernandes.dave.algamoney.api.model.Pessoa;

public interface PessoaRepository  extends JpaRepository<Pessoa, Integer>{

}
