package com.ifsp.projeto;

// Importações desnecessárias removidas (ArrayList, List)
import java.util.Optional; // Importação necessária para o findById

import org.springframework.beans.factory.annotation.Autowired; // Para injeção
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CarrinhoController {

    // 1. Injetando o Repositório no lugar da Lista
    @Autowired
    private ProdutoRepository repository;

    @GetMapping("/formulario")
    public String formularioProdutos() {
        return "index.html";
    }

    @GetMapping("/lista")
    public String mostrarLista(Model model) {
        // 2. Busca todos os produtos do BANCO DE DADOS
        model.addAttribute("produtos", repository.findAll());
        return "lista.html";
    }

    @PostMapping("/cadastrar")
    public String cadastrandoProduto(
            // 3. ID removido, pois agora é automático
            @RequestParam String nome,
            @RequestParam double preco,
            @RequestParam int quantidade,
            Model model) {

        // 4. Usa o construtor sem ID
        Produtos produtos = new Produtos(nome, preco, quantidade);
        // 5. Salva o produto no BANCO DE DADOS
        repository.save(produtos);
        return "redirect:/lista";
    }

    @GetMapping("/limpar")
    public String limparLista() {
        // 6. Limpa a tabela no BANCO DE DADOS
        repository.deleteAll();
        return "redirect:/lista";
    }

    @PostMapping("/adicionar")
    public String adicionarItem(@RequestParam int id) {
        // 7. Lógica de atualização com banco
        // Busca o produto pelo ID, e ele vem dentro de um "Optional"
        Optional<Produtos> produtoOpt = repository.findById(id);

        // Verifica se o produto realmente existe
        if (produtoOpt.isPresent()) {
            Produtos produto = produtoOpt.get(); // Extrai o produto do Optional
            produto.setQuantidade(produto.getQuantidade() + 1);
            repository.save(produto); // Salva a alteração
        }
        return "redirect:/lista";
    }


    @PostMapping("/diminuir")
    public String diminuirItem(@RequestParam int id) {
       
        Optional<Produtos> produtoOpt = repository.findById(id);

        if (produtoOpt.isPresent()) {
            Produtos produto = produtoOpt.get();
            int novaQuantidade = produto.getQuantidade() - 1;

            if (novaQuantidade <= 0) {
                // Se a quantidade for 0 ou menos, remove o item do banco
                repository.delete(produto);
            } else {
                // Se for maior que 0, só atualiza a quantidade
                produto.setQuantidade(novaQuantidade);
                repository.save(produto);
            }
        }

        return "redirect:/lista";
    }
    

    @PostMapping("/remover")
    public String removerItem(@RequestParam int id) {  
        // 9. Lógica de remoção com banco (muito mais simples)
        repository.deleteById(id);
        return "redirect:/lista";
    }
}