package dev.fernandes.dave.algamoney.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile("basic-security")
@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {
    
	@Autowired
	private UserDetailsService userDetailsService;
	
	private static final String[] PUBLIC_MATCHERS = { "/h2-console/**", "/app/**" };
	// private static final String[] PUBLIC_MATCHERS = { "/h2-console/**", "/**" };


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoderBasic());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			.anyRequest().authenticated()
			.and()
			.httpBasic()
			.and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.csrf().disable();
    }
    
    @Bean
    public PasswordEncoder passwordEncoderBasic() {
        return new BCryptPasswordEncoder();
    }

}