package com.ifsp.projeto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produtos, Long> {
    
    // [cite: 21] Implementa a busca usando operador LIKE automaticamente
    // O Spring entende "Containing" como "%texto%"
    List<Produtos> findByNomeContaining(String termo);
}