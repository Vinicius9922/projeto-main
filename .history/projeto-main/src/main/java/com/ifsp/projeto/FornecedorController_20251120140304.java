package com.ifsp.projeto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FornecedorController {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @GetMapping("/fornecedores")
    public String listarFornecedores(Model model) {
        model.addAttribute("listaFornecedores", fornecedorRepository.findAll());
        return "fornecedor-lista.html";
    }

    @GetMapping("/fornecedores/novo")
    public String novoFornecedor(Model model) {
        model.addAttribute("fornecedor", new Fornecedor());
        return "fornecedor-form.html";
    }

    @PostMapping("/fornecedores/salvar")
    public String salvarFornecedor(Fornecedor fornecedor) {
        fornecedorRepository.save(fornecedor);
        return "redirect:/fornecedores";
    }

    @GetMapping("/fornecedores/excluir/{id}")
    public String excluirFornecedor(@PathVariable Long id) {
        fornecedorRepository.deleteById(id);
        return "redirect:/fornecedores";
    }

    @GetMapping("/fornecedores/editar/{id}")
    public String editarFornecedor(@PathVariable Long id, Model model) {
        Fornecedor f = fornecedorRepository.findById(id).orElse(null);
        if (f != null) {
            model.addAttribute("fornecedor", f);
            return "fornecedor-form.html";
        }
        return "redirect:/fornecedores";
    }
}