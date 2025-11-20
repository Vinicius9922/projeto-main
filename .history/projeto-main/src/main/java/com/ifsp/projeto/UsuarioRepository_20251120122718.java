package com.ifsp.projeto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // O Spring usa esse método para buscar no login. 
    // Se o nome estiver errado, o login falha.
    Usuario findByEmail(String email);
}