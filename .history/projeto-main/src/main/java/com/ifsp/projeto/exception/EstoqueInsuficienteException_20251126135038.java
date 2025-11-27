package com.ifsp.projeto.exception;

// EXCEPTION: Criando nossa própria classe de erro
public class EstoqueInsuficienteException extends RuntimeException {
    public EstoqueInsuficienteException(String mensagem) {
        super(mensagem);
    }
}