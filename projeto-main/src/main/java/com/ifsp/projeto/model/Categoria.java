package com.ifsp.projeto.model;

import java.util.List;

import jakarta.persistence.Entity; 
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Categoria extends EntidadeBase{


    @NotBlank(message = "O nome da categoria é obrigatório") 
    private String nome;

    @OneToMany(mappedBy = "categoria")
    private List<Produtos> produtos;

    
    public Categoria() {
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