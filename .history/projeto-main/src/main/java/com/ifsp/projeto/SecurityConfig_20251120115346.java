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
                // PÁGINAS PÚBLICAS (Qualquer um acessa)
                .requestMatchers("/", "/lista", "/detalhes/**", "/login", "/css/**", "/imagens-upload/**").permitAll()
                .requestMatchers("/usuarios/novo", "/usuarios/salvar").permitAll() // Permitir criar conta
                
                // PÁGINAS RESTRITAS (Só ADMIN acessa)
                // O Spring entende "ADMIN" como "ROLE_ADMIN" automaticamente
                .requestMatchers("/formulario", "/cadastrar", "/editar/**", "/remover", "/usuarios/**", "/fornecedores/**").hasRole("ADMIN")
                
                // Qualquer outra requisição precisa estar logado
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login") // Nossa página customizada
                .defaultSuccessUrl("/lista", true) // Vai para a lista após logar
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