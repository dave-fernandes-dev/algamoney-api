package dev.fernandes.dave.algamoney.api.repository.filters;

import java.time.LocalDate;

import org.springframework.util.StringUtils;

//import org.springframework.format.annotation.DateTimeFormat;

public class LancamentoFilter {
	
	private String descricao;
	
	//@DateTimeFormat( pattern = "yyyy-MM-dd")
	private LocalDate dataVencimentoDe;
	
	//@DateTimeFormat( pattern = "yyyy-MM-dd")
	private LocalDate dataVencimentoAte;

	public String getDescricao() {
		return StringUtils.trimWhitespace(descricao);
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataVencimentoDe() {
		return dataVencimentoDe;
	}

	public void setDataVencimentoDe(LocalDate dataVencimentoDe) {
		this.dataVencimentoDe = dataVencimentoDe;
	}

	public LocalDate getDataVencimentoAte() {
		return dataVencimentoAte;
	}

	public void setDataVencimentoAte(LocalDate dataVencimentoAte) {
		this.dataVencimentoAte = dataVencimentoAte;
	}
	

	
}
