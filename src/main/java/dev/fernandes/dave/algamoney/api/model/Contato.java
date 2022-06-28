package dev.fernandes.dave.algamoney.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Entity @Data
@Table(name = "contato") 
public class Contato {
	
	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty
	private String nome;
	
	@Email @NotNull
	private String email;
	
	@NotEmpty
	private String telefone;
	
	@NotNull @ManyToOne @JoinColumn(name = "id_pessoa")
	private Pessoa pessoa;


}