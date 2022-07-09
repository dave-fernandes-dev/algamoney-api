package dev.fernandes.dave.algamoney.api.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import dev.fernandes.dave.algamoney.api.model.Contato;
import dev.fernandes.dave.algamoney.api.model.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the pessoa database table.
 * 
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class PessoaResumo implements Serializable {
	public PessoaResumo(Pessoa obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.ativo = obj.isAtivo();
		this.endereco = new EnderecoResumo(obj.getEndereco());
		this.contatos = obj.getContatos();
	}

	private static final long serialVersionUID = 1L;

	private int id;

	private String nome;

	private boolean ativo;
	
	@Embedded
	private EnderecoResumo endereco;

	@JsonIgnoreProperties("pessoa")
	private List<Contato> contatos;


}