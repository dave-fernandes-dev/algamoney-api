package dev.fernandes.dave.algamoney.api.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;


/**
 * The persistent class for the pessoa database table.
 * 
 */
@Entity @Data
@Table(name="pessoa")
public class Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty
	private String nome;

	@NotNull
	private boolean ativo;
	
	@Embedded
	Endereco endereco;
	
	@Valid
	@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
	private List<Contato> contatos;


}