package dev.fernandes.dave.algamoney.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data 
@ConfigurationProperties("algamoney")
@Component
public class AlgamoneyApiProperty {
	
	private String originPermitida = "http://localhost:4200";
	
	private final Seguranca seguranca = new Seguranca();
	
	@Data
	public
	static class Seguranca {
		
		private boolean enableHttps;
		
	}
	

}
