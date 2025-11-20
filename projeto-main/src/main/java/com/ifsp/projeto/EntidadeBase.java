package com.ifsp.projeto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

// ABSTRACT: Não pode ser instanciada sozinha, serve de molde
// MAPPED SUPERCLASS: Avisa ao banco que os campos daqui valem para os filhos
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