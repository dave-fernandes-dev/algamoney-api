package dev.fernandes.dave.algamoney.api.dto;

import java.math.BigDecimal;

import dev.fernandes.dave.algamoney.api.model.Categoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data @AllArgsConstructor @Builder
public class LancamentoEstatisticaByCategoria {
	
	private Categoria categoria;
	private BigDecimal total;

}
