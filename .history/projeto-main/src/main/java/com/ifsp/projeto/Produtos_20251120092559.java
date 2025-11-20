package com.ifsp.projeto;

// Importações do JPA (Jakarta Persistence)
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // 1. Anotação para dizer que esta classe é uma tabela do banco
@Table(name = "produtos") // 2. Define o nome da tabela (opcional, mas boa prática)
public class Produtos {
    
    @Id // 3. Define que este é o campo de Chave Primária (ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 4. Define que o ID será auto-incrementado pelo banco
    private int id;
    
    private String nome;
    private double preco;
    private int quantidade;

    // Construtor alterado (sem ID, pois o banco vai gerar)
    public Produtos(String nome, double preco, int quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    // Construtor vazio (Obrigatório pelo JPA)
    public Produtos() {
    }

    // Métodos Getters e Setters (não mudei nada aqui)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}