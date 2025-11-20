package com.ifsp.projeto;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CarrinhoController {

    @Autowired
    private ProdutoRepository repository;

    @GetMapping("/formulario")
    public String formularioProdutos() {
        return "index.html";
    }

    @GetMapping("/lista")
    public String mostrarLista(Model model) {
        model.addAttribute("produtos", repository.findAll());
        return "lista.html";
    }

    @PostMapping("/cadastrar")
    public String cadastrandoProduto(
            @RequestParam String nome,
            @RequestParam double preco,
            @RequestParam int quantidade,
            Model model) {

        // CORREÇÃO 1: Usando o construtor vazio e setters
        // (Porque a nova classe Produtos mudou a estrutura)
        Produtos produtos = new Produtos();
        produtos.setNome(nome);
        produtos.setPreco(preco);
        produtos.setQuantidade(quantidade);
        
        // Por enquanto, salvamos sem imagem e sem categoria
        // Faremos isso na próxima etapa (Upload de Imagens)
        repository.save(produtos);
        
        return "redirect:/lista";
    }

    @GetMapping("/limpar")
    public String limparLista() {
        repository.deleteAll();
        return "redirect:/lista";
    }

    @PostMapping("/adicionar")
    public String adicionarItem(@RequestParam Long id) { // CORREÇÃO 2: Mudei int para Long
        Optional<Produtos> produtoOpt = repository.findById(id);

        if (produtoOpt.isPresent()) {
            Produtos produto = produtoOpt.get();
            produto.setQuantidade(produto.getQuantidade() + 1);
            repository.save(produto);
        }
        return "redirect:/lista";
    }

    @PostMapping("/diminuir")
    public String diminuirItem(@RequestParam Long id) { // CORREÇÃO 2: Mudei int para Long
       
        Optional<Produtos> produtoOpt = repository.findById(id);

        if (produtoOpt.isPresent()) {
            Produtos produto = produtoOpt.get();
            int novaQuantidade = produto.getQuantidade() - 1;
            
            if (novaQuantidade <= 0) {
                repository.deleteById(id);
            } else {
                produto.setQuantidade(novaQuantidade);
                repository.save(produto);
            }
        }

        return "redirect:/lista";
    }
    
    @PostMapping("/remover")
    public String removerItem(@RequestParam Long id) { // CORREÇÃO 2: Mudei int para Long
        repository.deleteById(id);
        return "redirect:/lista";
    }
}