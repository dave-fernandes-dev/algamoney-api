package dev.fernandes.dave.algamoney.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String[] PUBLIC_MATCHERS = { "/h2-console/**", "/categorias" };
	//private static final String[] PUBLIC_MATCHERS = { "/h2-console/**", "/**" };

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// {noop}admin  é para enviar sem password encoder o qual NÃO é recomendado
		auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("ROLE");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers(PUBLIC_MATCHERS).permitAll()
				.anyRequest().authenticated()
			.and()
				.httpBasic()
			.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				//por nao ter aplicacao web embutida podemos desabilitar o CSRF (javascript injection)
				.csrf().disable();
	}

}
