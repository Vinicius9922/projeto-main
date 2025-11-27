package com.ifsp.projeto.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.ifsp.projeto.model.Categoria;
import com.ifsp.projeto.repository.CategoriaRepository;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public void run(String... args) throws Exception {
        if (categoriaRepository.count() == 0) {
            Categoria c1 = new Categoria();
            c1.setNome("Eletrônicos");
            Categoria c2 = new Categoria();
            c2.setNome("Móveis");
            Categoria c3 = new Categoria();
            c3.setNome("Roupas");
            Categoria c4 = new Categoria();
            c4.setNome("Livros");

            categoriaRepository.saveAll(Arrays.asList(c1, c2, c3, c4));
            System.out.println("--- Categorias Iniciais Criadas! ---");
        }
    }
}