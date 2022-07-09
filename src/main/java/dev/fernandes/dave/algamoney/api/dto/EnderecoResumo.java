package dev.fernandes.dave.algamoney.api.dto;

import javax.persistence.Embeddable;

import dev.fernandes.dave.algamoney.api.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Embeddable 
public class EnderecoResumo {
	
	public EnderecoResumo(Endereco obj) {
		this.bairro = obj.getBairro();
		this.cep = obj.getCep();
		this.cidade = obj.getCidade().getNome();
		this.estado = obj.getCidade().getEstado().getNome();
		this.complemento = obj.getComplemento();
		this.logradouro = obj.getLogradouro();
		this.numero = obj.getNumero();
	}

	private String bairro;
	private String cep;
	private String cidade;
	private String estado;	
	private String complemento;
	private String logradouro;
	private String numero;


}
