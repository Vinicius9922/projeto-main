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
        System.out.println("--- TENTATIVA DE LOGIN ---");
        System.out.println("Email recebido: " + email);

        Usuario usuario = repository.findByEmail(email);

        if (usuario == null) {
            System.out.println("ERRO: Usuário não encontrado no banco!");
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        System.out.println("Usuário encontrado: " + usuario.getNome());
        System.out.println("Senha no banco (Criptografada): " + usuario.getSenha());
        System.out.println("Papel: " + usuario.getPapel());

        String papel = usuario.getPapel();
        if (papel == null || papel.isEmpty()) {
            papel = "USER"; // Define um padrão se estiver vazio
        }

        if (!papel.startsWith("ROLE_")) {
            papel = "ROLE_" + papel;
        }

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(papel.replace("ROLE_", ""))
                .build();
    }
}