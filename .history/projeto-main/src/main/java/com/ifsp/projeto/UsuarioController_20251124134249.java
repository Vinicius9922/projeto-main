package com.ifsp.projeto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    // 1. Listar Usuários
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("listaUsuarios", usuarioRepository.findAll());
        return "usuario-lista.html"; // Vamos criar este arquivo
    }

    // 2. Abrir formulário de cadastro
    @GetMapping("/usuarios/novo")
    public String novoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario-form.html"; // Vamos criar este arquivo
    }

    // 3. Salvar Usuário
    @PostMapping("/usuarios/salvar")
    public String salvarUsuario(Usuario usuario) {
        // CRIPTOGRAFIA DA SENHA
        String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        usuarioRepository.save(usuario);
        return "redirect:/usuarios";
    }

    
    @GetMapping("/usuarios/excluir/{id}")
    public String excluirUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return "rediect:/usuarios";
    }

  
    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            model.addAttribute("usuario", usuario);
            return "usuario-form.html";
        }
        return "redirect:/usuarios";
    }

}