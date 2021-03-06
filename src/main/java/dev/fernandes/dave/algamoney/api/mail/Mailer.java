package dev.fernandes.dave.algamoney.api.mail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import dev.fernandes.dave.algamoney.api.model.Lancamento;
import dev.fernandes.dave.algamoney.api.model.Usuario;
import dev.fernandes.dave.algamoney.api.repository.LancamentoRepository;

@Configuration
public class Mailer {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private TemplateEngine thymeleaf;
	
	@Autowired
	private LancamentoRepository lr;
	
	@SuppressWarnings("unused")
	//@EventListener //descomente para enviar email
	private void testeMailTemplate(ApplicationReadyEvent event) {
		
		String template = "thymeleaf/mail/aviso-lancamentos-vencidos";
		
		List<Lancamento> lista = lr.findAll();
		
		Map<String, Object> variaveis = new HashMap<>();
		variaveis.put("lancamentos", lista);
		
		List<String> destinatarios = Arrays.asList("dave.analista@gmail.com", "consubr@yahoo.com.br");
		
		this.enviarEmail("testes.algaworks@gmail.com", destinatarios , "Testando Mail Template", template, variaveis);
		System.out.println(">>>>>>>Terminado envio email template");
	}
	
	@SuppressWarnings("unused")
	//@EventListener //descomente para enviar email ao levantar a aplicação
	private void teste(ApplicationReadyEvent event) {
		this.enviarEmail("testes.algaworks@gmail.com", Arrays.asList("dave.analista@gmail.com", "consubr@yahoo.com.br"), "Testando", "Olá!<br/>Teste ok.");
		System.out.println(">>>>>>>Terminado envio email");
	}
	
	public void enviarEmail(String remetente, List<String> destinatarios, String assunto, String template, Map<String, Object> variaveis) {
		
		Context context = new Context(new Locale("pt","BR"));
		
		variaveis.entrySet().forEach(e -> context.setVariable(e.getKey(),  e.getValue()));
		
		String mensagem = thymeleaf.process(template, context);
		
		this.enviarEmail(remetente, destinatarios, assunto, mensagem);
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

	public void avisarSobreLancamentosVencidos(List<Lancamento> vencidos, List<Usuario> destinatarios) {
		
		Map<String, Object> variaveis = new HashMap<>();
		variaveis.put("lancamentos", vencidos);
		
		List<String> emails = destinatarios.stream().map(u -> u.getEmail()).collect(Collectors.toList());
		
		String template = "thymeleaf/mail/aviso-lancamentos-vencidos";
		this.enviarEmail("testes.algaworks@gmail.com", emails , "Lancamentos Vencidos", template, variaveis);		
		
	}
}
