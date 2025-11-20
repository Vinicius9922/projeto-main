package com.ifsp.projeto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Método extra para buscar por email (será útil no login futuro)
    Usuario findByEmail(String email);
}