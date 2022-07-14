package dev.fernandes.dave.algamoney.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data 
@ConfigurationProperties("algamoney")
@Component @Primary
public class AlgamoneyApiProperty {
	
	//private String originPermitida = "http://localhost:4200";
	private String originPermitida;
	
	private final Seguranca seguranca = new Seguranca();
	
	@Data
	public static class Seguranca {
		
		private boolean enableHttps;
		
	}
	

	private final Mail mail = new Mail();	
	
	@Data
	public static class Mail {
		
		private String host;		
		private Integer port;		
		private String username;		
		private String password;
	}
	
	private final S3 s3 = new S3();
	
	@Data
	public static class S3 {
		// heroku não conseguia ler estas variaveis, por isso usei: System.getenv("")
		private String accessKeyId=System.getenv("AWS_S3_ACCESS_KEY");
		private String secretKey=System.getenv("AWS_S3_SECRET_KEY");
		
		//private String accessKeyId;
		//private String secretKey;

		private String bucket = "vl-algamoney-arquivos";

		
	}
	

}
