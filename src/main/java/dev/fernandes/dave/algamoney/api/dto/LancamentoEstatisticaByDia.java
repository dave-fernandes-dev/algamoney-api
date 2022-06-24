package dev.fernandes.dave.algamoney.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import dev.fernandes.dave.algamoney.api.model.enums.TipoLancamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data @AllArgsConstructor @Builder
public class LancamentoEstatisticaByDia {
	
	private TipoLancamento tipo;
	private LocalDate dia;
	private BigDecimal total;

}
