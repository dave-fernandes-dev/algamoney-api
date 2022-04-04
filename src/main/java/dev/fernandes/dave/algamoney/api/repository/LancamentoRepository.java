package dev.fernandes.dave.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.fernandes.dave.algamoney.api.model.Lancamento;
import dev.fernandes.dave.algamoney.api.repository.query.LancamentoRepositoryQuery;

public interface LancamentoRepository  extends JpaRepository<Lancamento, Integer>, LancamentoRepositoryQuery {

}
