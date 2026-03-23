package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/", "/home", "/error", "/register", "/login").permitAll()
				.requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/static/**").permitAll()
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.requestMatchers("/enroll/**").hasRole("STUDENT")
				.requestMatchers("/my-courses").hasRole("STUDENT")
				.requestMatchers("/courses", "/courses/**").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin(form -> form
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/home", true)
				.failureUrl("/login?error")
				.permitAll()
			)
			.logout(logout -> logout
				.logoutSuccessUrl("/home")
				.permitAll()
			);

		return http.build();
	}
}
