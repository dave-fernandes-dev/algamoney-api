package dev.fernandes.dave.algamoney.api.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;


/**
 * The persistent class for the categorias database table.
 * 
 */
@Entity @Data
@Table(name="categoria")
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	//@Column(unique=true, nullable=false)
	private int id;

	//@Column(nullable=false, length=50) 
	@NotEmpty() @Size(min = 3, max = 20)
	private String nome;
	

}