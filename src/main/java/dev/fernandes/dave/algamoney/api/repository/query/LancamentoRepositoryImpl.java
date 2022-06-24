package dev.fernandes.dave.algamoney.api.repository.query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import dev.fernandes.dave.algamoney.api.dto.LancamentoEstatisticaByCategoria;
import dev.fernandes.dave.algamoney.api.dto.ResumoLancamento;
import dev.fernandes.dave.algamoney.api.model.Lancamento;
import dev.fernandes.dave.algamoney.api.repository.filters.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;
	
	
	@Override
	public List<LancamentoEstatisticaByCategoria> byCategoria(LocalDate mesReferencia) {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		
		CriteriaQuery<LancamentoEstatisticaByCategoria> cq = cb.createQuery(LancamentoEstatisticaByCategoria.class);
		
		Root<Lancamento> root = cq.from(Lancamento.class); 

		cq.select(cb.construct(LancamentoEstatisticaByCategoria.class, root.get("categoria"), cb.sum(root.get("valor"))));
		
		LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());
		
		cq.where(cb.greaterThanOrEqualTo(root.get("dataVencimento"), primeiroDia));
		cq.where(cb.lessThanOrEqualTo(root.get("dataVencimento"), ultimoDia));
		
		cq.groupBy(root.get("categoria"));
		
		TypedQuery<LancamentoEstatisticaByCategoria> typedQuery = manager.createQuery(cq);
		
		return typedQuery.getResultList();
	}
	
	public List<Lancamento> filtrar(LancamentoFilter filter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class); 
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		//criar as restrições
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);
		
		criteria.orderBy(builder.desc(root.get("id")));
		
		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		return query.getResultList();
	}
	
	@Override
	public Page<ResumoLancamento> resumirPaginado(LancamentoFilter filter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class); 
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		criteria.select((builder.construct(ResumoLancamento.class
				, root.get("id"), root.get("descricao"), root.get("dataVencimento")
				, root.get("dataPagamento"), root.get("valor"), root.get("tipo")
				, root.get("categoria").get("nome")
				, root.get("pessoa").get("nome")
		)));
		
		//criar as restrições
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);

		criteria.orderBy(builder.desc(root.get("id")));
		
		TypedQuery<ResumoLancamento> query = manager.createQuery(criteria);
		
		addRestricaoPaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(filter));
	}
	

	@Override
	public Page<Lancamento> filtrarPaginado(LancamentoFilter filter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class); 
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		//criar as restrições
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);
		
		criteria.orderBy(builder.desc(root.get("id")));
		
		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		
		addRestricaoPaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(filter));
	}

	private Predicate[] criarRestricoes(LancamentoFilter filter, CriteriaBuilder builder,
			Root<Lancamento> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if (StringUtils.isNotBlank(filter.getDescricao())) {
		//if (filter.getDescricao() != null ) {
			predicates.add(builder.like(root.get("descricao"), "%" + filter.getDescricao().toLowerCase() + "%"));			
		}
		
		if (filter.getDataVencimentoDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataVencimento"), filter.getDataVencimentoDe()));
		}
		
		if (filter.getDataVencimentoAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataVencimento"), filter.getDataVencimentoAte()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void addRestricaoPaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);		
	}
	
	private long total(LancamentoFilter filter) {
		CriteriaBuilder builder  = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(filter, builder, root);		
		criteria.where(predicates);		
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
	}





}
