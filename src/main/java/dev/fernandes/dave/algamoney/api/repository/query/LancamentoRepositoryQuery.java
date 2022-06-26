package dev.fernandes.dave.algamoney.api.repository.query;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dev.fernandes.dave.algamoney.api.dto.LancamentoEstatisticaByCategoria;
import dev.fernandes.dave.algamoney.api.dto.LancamentoEstatisticaByDia;
import dev.fernandes.dave.algamoney.api.dto.ResumoLancamento;
import dev.fernandes.dave.algamoney.api.model.Lancamento;
import dev.fernandes.dave.algamoney.api.repository.filters.LancamentoFilter;

public interface LancamentoRepositoryQuery {
	
	public List<LancamentoEstatisticaByCategoria> byCategoria(LocalDate mesReferencia);
	
	public List<LancamentoEstatisticaByDia> byDia(LocalDate mesReferencia);
	
	public List<Lancamento> filtrar(LancamentoFilter filter);
	
	public Page<Lancamento> filtrarPaginado(LancamentoFilter filter, Pageable pageable);
	
	public Page<ResumoLancamento> resumirPaginado(LancamentoFilter filter, Pageable pageable);
	
	

}
