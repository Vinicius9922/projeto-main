package com.ifsp.projeto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ifsp.projeto.CategoriaRepository;
import com.ifsp.projeto.model.Categoria;

@Controller
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/categorias")
    public String listarCategorias(Model model) {
        model.addAttribute("listaCategorias", categoriaRepository.findAll());
        return "categoria-lista.html";
    }

    @GetMapping("/categorias/novo")
    public String novaCategoria(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categoria-form.html";
    }

    @PostMapping("/categorias/salvar")
    public String salvarCategoria(Categoria categoria) {
        categoriaRepository.save(categoria);
        return "redirect:/categorias";
    }

    @GetMapping("/categorias/excluir/{id}")
    public String excluirCategoria(@PathVariable Long id) {
        try {
            categoriaRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("Não pode excluir categoria com produtos!");
        }
        return "redirect:/categorias";
    }

    @GetMapping("/categorias/editar/{id}")
    public String editarCategoria(@PathVariable Long id, Model model) {
        Categoria c = categoriaRepository.findById(id).orElse(null);
        if (c != null) {
            model.addAttribute("categoria", c);
            return "categoria-form.html";
        }
        return "redirect:/categorias";
    }
}