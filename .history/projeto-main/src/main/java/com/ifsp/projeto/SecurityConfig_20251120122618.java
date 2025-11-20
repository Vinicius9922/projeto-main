package com.ifsp.projeto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                // PÁGINAS PÚBLICAS
                // Mudamos "/css/**" para "/*.css" para pegar seus arquivos na raiz
                .requestMatchers("/", "/lista", "/detalhes/**", "/login", "/*.css", "/imagens-upload/**").permitAll()
                .requestMatchers("/usuarios/novo", "/usuarios/salvar").permitAll()
                
                // PÁGINAS RESTRITAS
                .requestMatchers("/formulario", "/cadastrar", "/editar/**", "/remover", "/usuarios/**", "/fornecedores/**", "/categorias/**").hasRole("ADMIN")
                
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .defaultSuccessUrl("/lista", true)
                .permitAll()
            )
            .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Define o criptografador padrão
    }
}