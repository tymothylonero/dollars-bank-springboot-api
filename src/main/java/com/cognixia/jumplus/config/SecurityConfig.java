package com.cognixia.jumplus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/api/account/*").permitAll()
			.antMatchers(HttpMethod.POST, "/api/account/add").permitAll()
			.antMatchers(HttpMethod.PUT, "/api/account/update").permitAll()
			.antMatchers(HttpMethod.DELETE, "/api/account/delete/*").permitAll()
			.antMatchers(HttpMethod.GET, "/api/account/username/*").permitAll()
			.antMatchers(HttpMethod.PUT, "/api/account/username/deposit/*/*").permitAll()
			.antMatchers(HttpMethod.PUT, "/api/account/username/withdraw/*/*").permitAll()
			.antMatchers(HttpMethod.GET, "/api/transaction/five_recent/*").permitAll()
			.antMatchers(HttpMethod.POST, "/api/transaction/add").permitAll()
			.antMatchers(HttpMethod.GET, "/api/transaction/*").permitAll()
			.antMatchers(HttpMethod.PUT, "/api/transaction/transfer").permitAll()
			.antMatchers("/**").hasRole("ADMIN")
			.anyRequest().authenticated()
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	}
	
}