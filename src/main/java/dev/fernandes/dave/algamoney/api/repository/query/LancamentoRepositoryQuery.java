package dev.fernandes.dave.algamoney.api.repository.query;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dev.fernandes.dave.algamoney.api.model.Lancamento;
import dev.fernandes.dave.algamoney.api.repository.filters.LancamentoFilter;

public interface LancamentoRepositoryQuery {
	
	public List<Lancamento> filtrar(LancamentoFilter filter);
	
	public Page<Lancamento> filtrarPaginado(LancamentoFilter filter, Pageable pageable);
	
	

}
