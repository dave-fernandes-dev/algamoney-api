package dev.fernandes.dave.algamoney.api.security.util;

import java.time.Duration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorDeSenha {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("admin"));
		System.out.println((int)Duration.ofDays(1).getSeconds());
	
}
}
