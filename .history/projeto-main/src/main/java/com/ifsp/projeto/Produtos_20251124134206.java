package com.ifsp.projeto;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn; // Para validações
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "produtos")
public class Produtos extends EntidadeBase{ // Mantivemos o nome da classe que você já tinha

   
    @NotBlank(message = "O nome é obrigatório") // Validação 2
    private String nome;

    @Positive(message = "O preço deve ser maior que zero") // Validação 3
    private double preco;

    @Min(value = 0, message = "A quantidade não pode ser negativa")
    private int quantidade;

    private String imagem;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    // Construtores
    public Produtos() {
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

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}