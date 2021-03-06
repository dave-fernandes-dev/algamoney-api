package dev.fernandes.dave.algamoney.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import dev.fernandes.dave.algamoney.api.model.enums.TipoLancamento;
import dev.fernandes.dave.algamoney.api.repository.listener.LancamentoAnexoListener;
import lombok.Data;

@Entity @EntityListeners(LancamentoAnexoListener.class)
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
	
	private String anexo;
	
	@Transient
	private String urlAnexo;

	@Enumerated(EnumType.STRING)
	private TipoLancamento tipo;

	@NotNull(message = "O campo Categoria é obrigatório")
	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private Categoria categoria;

	@JsonIgnoreProperties("contatos") //irá ignorar contatos da tabela pessoa
	@NotNull(message = "O campo Pessoa é obrigatório")
	@ManyToOne(optional = false)
	@JoinColumn(name = "id_pessoa")
	private Pessoa pessoa;
	
	@JsonIgnore
	public boolean isReceita() {
		return TipoLancamento.RECEITA.equals(this.tipo);
	}

}