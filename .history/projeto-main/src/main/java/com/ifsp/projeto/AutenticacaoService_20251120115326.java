package com.ifsp.projeto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repository.findByEmail(email);
        
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        // Transforma o nosso Usuario no User do Spring Security
        // Adiciona o prefixo "ROLE_" porque o Spring exige isso internamente
        String papel = usuario.getPapel(); 
        if (!papel.startsWith("ROLE_")) {
            papel = "ROLE_" + papel; 
        }

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(papel.replace("ROLE_", "")) // O builder adiciona o prefixo sozinho
                .build();
    }
}