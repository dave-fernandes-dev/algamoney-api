package dev.fernandes.dave.algamoney.api.model;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable @Data
public class Endereco {
	

	private String bairro;

	private String cep;

	private String cidade;

	private String complemento;

	private String estado;

	private String logradouro;

	private String numero;


}
