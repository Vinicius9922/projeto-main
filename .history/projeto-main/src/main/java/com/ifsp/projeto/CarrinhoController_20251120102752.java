package com.ifsp.projeto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CarrinhoController {

    @Autowired
    private ProdutoRepository repository;
    
    // Caminho onde as imagens serão salvas
    private static String CAMINHO_IMAGENS = "./imagens-upload/";

    @GetMapping("/formulario")
    public String formularioProdutos(Model model) {
        model.addAttribute("produtos", new Produtos()); // Necessário para validação futura
        return "index.html";
    }

    @GetMapping("/lista")
    public String mostrarLista(@RequestParam(required = false) String busca, Model model) {
        // Requisito 3: BUSCA 
        // Se o usuário digitou algo na busca, filtramos. Se não, trazemos tudo.
        if (busca != null && !busca.isEmpty()) {
            model.addAttribute("produtos", repository.findByNomeContaining(busca));
        } else {
            model.addAttribute("produtos", repository.findAll());
        }
        return "lista.html";
    }

    @PostMapping("/cadastrar")
    public String cadastrandoProduto(
            @RequestParam String nome,
            @RequestParam double preco,
            @RequestParam int quantidade,
            @RequestParam("file") MultipartFile arquivo, // Requisito 4: Upload [cite: 22, 23]
            Model model) {

        Produtos produtos = new Produtos();
        produtos.setNome(nome);
        produtos.setPreco(preco);
        produtos.setQuantidade(quantidade);
        
        // Lógica de Upload de Imagem
        try {
            if (!arquivo.isEmpty()) {
                byte[] bytes = arquivo.getBytes();
                // Define o caminho onde a imagem será salva
                Path caminho = Paths.get(CAMINHO_IMAGENS + arquivo.getOriginalFilename());
                // Escreve a imagem na pasta
                Files.write(caminho, bytes);
                // Salva apenas o NOME do arquivo no banco de dados
                produtos.setImagem(arquivo.getOriginalFilename());
            }
        } catch (IOException e) {
            e.printStackTrace(); // Em um projeto real, trataríamos o erro melhor
        }

        repository.save(produtos);
        return "redirect:/lista";
    }

    @GetMapping("/limpar")
    public String limparLista() {
        repository.deleteAll();
        return "redirect:/lista";
    }

    @PostMapping("/adicionar")
    public String adicionarItem(@RequestParam Long id) {
        Optional<Produtos> produtoOpt = repository.findById(id);
        if (produtoOpt.isPresent()) {
            Produtos produto = produtoOpt.get();
            produto.setQuantidade(produto.getQuantidade() + 1);
            repository.save(produto);
        }
        return "redirect:/lista";
    }

    @PostMapping("/diminuir")
    public String diminuirItem(@RequestParam Long id) {
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
    public String removerItem(@RequestParam Long id) {  
        repository.deleteById(id);
        return "redirect:/lista";
    }
}