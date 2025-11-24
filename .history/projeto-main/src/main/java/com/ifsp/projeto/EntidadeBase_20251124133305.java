package com.ifsp.projeto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;


@MappedSuperclass 
public abstract class EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Getter e Setter (Herdado por todo mundo)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}