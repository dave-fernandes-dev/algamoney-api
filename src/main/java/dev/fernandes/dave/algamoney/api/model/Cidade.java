package dev.fernandes.dave.algamoney.api.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data @AllArgsConstructor @NoArgsConstructor
@Entity 
@Table(name="cidade")
public class Cidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String nome;

	//bi-directional many-to-one association to Estado
	@ManyToOne @JoinColumn(name="id_estado")
	private Estado estado;


}