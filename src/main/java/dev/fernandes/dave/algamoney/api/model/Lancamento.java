package dev.fernandes.dave.algamoney.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.fernandes.dave.algamoney.api.model.enums.TipoLancamento;
import lombok.Data;

@Entity
@Table(name = "lancamento") @Data
public class Lancamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String descricao;

	@Column(name = "data_vencimento")
	private LocalDate dataVencimento;

	@Column(name = "data_pagamento")
	private LocalDate dataPagamento;

	private BigDecimal valor;

	private String observacao;

	@Enumerated(EnumType.STRING)
	private TipoLancamento tipo;

	@NotNull(message = "O campo Categoria é obrigatório")
	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private Categoria categoria;

	@NotNull(message = "O campo Pessoa é obrigatório")
	@ManyToOne(optional = false)
	@JoinColumn(name = "id_pessoa")
	private Pessoa pessoa;
	
	@JsonIgnore
	public boolean isReceita() {
		return TipoLancamento.RECEITA.equals(this.tipo);
	}

}