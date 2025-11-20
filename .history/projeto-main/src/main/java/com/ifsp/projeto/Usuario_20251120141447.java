package com.ifsp.projeto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Usuario extends EntidadeBase{
  
    private String nome;

    @Column(unique = true)
    private String email;

    private String senha;

    private String papel; // Ex: "ADMIN" ou "USER"

    // Construtores, Getters e Setters
    public Usuario() {
    }

   
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }
}