package dev.fernandes.dave.algamoney.api.mail;

import java.util.Arrays;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Configuration
public class Mailer {

	@Autowired
	private JavaMailSender mailSender;
	
	//@EventListener //descomente para enviar email
	private void teste(ApplicationReadyEvent event) {
		this.enviarEmail("testes.algaworks@gmail.com",
				Arrays.asList("dave.analista@gmail.com", "consubr@yahoo.com.br"),
				"Testando", "Olá!<br/>Teste ok.");
		System.out.println(">>>>>>>Terminado envio email");
	}
	
	public void enviarEmail(String remetente, List<String> destinatarios, String assunto, String mensagem) {
		
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setFrom(remetente);
			helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
			helper.setSubject(assunto);
			helper.setText(mensagem, true);
			
			mailSender.send(mimeMessage);
			
		} catch (Exception e) {
			throw new RuntimeException("Problema com o envio de email", e);
		}
	}
}
