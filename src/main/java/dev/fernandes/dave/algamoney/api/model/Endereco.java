package dev.fernandes.dave.algamoney.api.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Embeddable 
public class Endereco {
	

	private String bairro;
	private String cep;
	
	@ManyToOne @JoinColumn(name="id_cidade")
	private Cidade cidade;
	
	private String complemento;
	private String logradouro;
	private String numero;


}
