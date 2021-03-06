package dev.fernandes.dave.algamoney.api.model.enums;

public enum TipoLancamento {

	RECEITA("Receita"),
	DESPESA("Despesa");
	
	private final String descricao;
	
	TipoLancamento(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
}