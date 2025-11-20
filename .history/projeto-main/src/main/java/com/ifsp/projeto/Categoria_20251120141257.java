package com.ifsp.projeto;

import java.util.List;

import jakarta.persistence.Entity; //  Validação
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Categoria extends EntidadeBase{


    @NotBlank(message = "O nome da categoria é obrigatório") // Validação 1
    private String nome;

    @OneToMany(mappedBy = "categoria")
    private List<Produtos> produtos;

    // Construtores, Getters e Setters
    public Categoria() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Produtos> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produtos> produtos) {
        this.produtos = produtos;
    }
}