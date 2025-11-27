package com.ifsp.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifsp.projeto.model.Produtos;

@Repository
public interface ProdutoRepository extends JpaRepository<Produtos, Long> {
    List<Produtos> findByNomeContaining(String termo);
}