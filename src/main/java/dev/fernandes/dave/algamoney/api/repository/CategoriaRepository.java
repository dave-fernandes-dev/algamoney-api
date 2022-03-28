package dev.fernandes.dave.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.fernandes.dave.algamoney.api.model.Categoria;

public interface CategoriaRepository  extends JpaRepository<Categoria, Integer>{

}
