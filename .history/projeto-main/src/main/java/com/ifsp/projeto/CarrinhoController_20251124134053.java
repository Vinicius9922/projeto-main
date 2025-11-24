package com.ifsp.projeto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // Importante para validação
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class CarrinhoController {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    private static String CAMINHO_IMAGENS = "./imagens-upload/";

    
    @GetMapping("/")
    public String home() {
        return "redirect:/lista";
    }

    @GetMapping("/formulario")
    public String formularioProdutos(Model model) {
        model.addAttribute("produtos", new Produtos());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "index.html";
    }

    @GetMapping("/lista")
    public String mostrarLista(@RequestParam(required = false) String busca, Model model) {
        if (busca != null && !busca.isEmpty()) {
            model.addAttribute("produtos", repository.findByNomeContaining(busca));
        } else {
            model.addAttribute("produtos", repository.findAll());
        }
        return "lista.html";
    }

    @GetMapping("/detalhes/{id}")
    public String verDetalhes(@PathVariable Long id, Model model) {
        Optional<Produtos> produtoOpt = repository.findById(id);
        if (produtoOpt.isEmpty()) {
            return "redirect:/lista";
        }
        model.addAttribute("produto", produtoOpt.get());
        return "detalhes.html";
    }

    
    @PostMapping("/cadastrar")
    public String cadastrandoProduto(
            @Valid Produtos produtos, 
            BindingResult result, 
            @RequestParam Long categoriaId,
            @RequestParam("file") MultipartFile arquivo,
            Model model) {

        
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaRepository.findAll());
            return "index.html";
        }

        Optional<Categoria> catOpt = categoriaRepository.findById(categoriaId);
        if (catOpt.isPresent()) {
            produtos.setCategoria(catOpt.get());
        }

        try {
            if (!arquivo.isEmpty()) {
                byte[] bytes = arquivo.getBytes();
                Path caminho = Paths.get(CAMINHO_IMAGENS + arquivo.getOriginalFilename());
                Files.write(caminho, bytes);
                produtos.setImagem(arquivo.getOriginalFilename());
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            Produtos p = produtoOpt.get();
            p.setQuantidade(p.getQuantidade() + 1);
            repository.save(p);
        }
        return "redirect:/lista";
    }

    @PostMapping("/diminuir")
    public String diminuirItem(@RequestParam Long id) {
        Optional<Produtos> produtoOpt = repository.findById(id);
        if (produtoOpt.isPresent()) {
            Produtos p = produtoOpt.get();
            int novaQtd = p.getQuantidade() - 1;
            if (novaQtd <= 0)
                repository.deleteById(id);
            else {
                p.setQuantidade(novaQtd);
                repository.save(p);
            }
        }
        return "redirect:/lista";
    }

    @PostMapping("/remover")
    public String removerItem(@RequestParam Long id) {
        repository.deleteById(id);
        return "redirect:/lista";
    }

   @PostMapping("/comprar")
    public String comprarProduto(@RequestParam Long id, 
                                 @RequestParam int quantidadeCompra, 
                                 RedirectAttributes attributes) {
        
        try {
            processarCompra(id, quantidadeCompra);
            attributes.addFlashAttribute("sucesso", "Compra realizada com sucesso!");
            
        } catch (EstoqueInsuficienteException e) {
            
            attributes.addFlashAttribute("erro", e.getMessage());
        } catch (Exception e) {
            
            attributes.addFlashAttribute("erro", "Erro inesperado na compra.");
        }
        
        return "redirect:/lista";
    }

    private void processarCompra(Long id, int qtd) {
        Optional<Produtos> produtoOpt = repository.findById(id);
        if (produtoOpt.isPresent()) {
            Produtos produto = produtoOpt.get();
            
            if (qtd <= 0) {
                throw new EstoqueInsuficienteException("Quantidade inválida.");
            }
            if (qtd > produto.getQuantidade()) {
                
                throw new EstoqueInsuficienteException("Estoque insuficiente! Restam apenas " + produto.getQuantidade());
            }

            produto.setQuantidade(produto.getQuantidade() - qtd);
            repository.save(produto);
        }
    }
}