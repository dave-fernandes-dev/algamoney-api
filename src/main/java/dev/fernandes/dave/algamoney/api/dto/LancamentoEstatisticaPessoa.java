package dev.fernandes.dave.algamoney.api.dto;

import java.math.BigDecimal;

import dev.fernandes.dave.algamoney.api.model.Pessoa;
import dev.fernandes.dave.algamoney.api.model.enums.TipoLancamento;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class LancamentoEstatisticaPessoa {
	
	private TipoLancamento tipo;
	private Pessoa pessoa;
	private BigDecimal total;
	
	

}
