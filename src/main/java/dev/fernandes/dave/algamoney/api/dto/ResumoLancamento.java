package dev.fernandes.dave.algamoney.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import dev.fernandes.dave.algamoney.api.model.enums.TipoLancamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor //tirei p usar construtor definido
@NoArgsConstructor
@Builder
public class ResumoLancamento {
	
	private int id;
	private String descricao;
	private LocalDate dataVencimento;
	private LocalDate dataPagamento;
	private BigDecimal valor;
	private TipoLancamento tipo;
	private String categoria;
	private String pessoa;
	
	/*
	 * public ResumoLancamento(int id, String descricao, LocalDate dataVencimento,
	 * LocalDate dataPagamento, BigDecimal valor, TipoLancamento tipo, String
	 * categoria, String pessoa) { this.id = id; this.descricao = descricao;
	 * this.dataVencimento = dataVencimento; this.dataPagamento = dataPagamento;
	 * this.valor = valor; this.tipo = tipo; this.categoria = categoria; this.pessoa
	 * = pessoa; }
	 */
	
}
