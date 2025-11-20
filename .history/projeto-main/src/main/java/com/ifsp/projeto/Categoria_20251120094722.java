package com.ifsp.projeto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank; //  Validação
import java.util.List;

@Entity
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da categoria é obrigatório") //  Validação 1
    private String nome;

    @OneToMany(mappedBy = "categoria")
    private List<Produtos> produtos;

    // Construtores, Getters e Setters
    public Categoria() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public List<Produtos> getProdutos() { return produtos; }
    public void setProdutos(List<Produtos> produtos) { this.produtos = produtos; }
}