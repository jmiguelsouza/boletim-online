package com.example.boletimonline.controller;

import com.example.boletimonline.model.Usuario;
import com.example.boletimonline.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class LoginController {

    private final UsuarioRepository usuarioRepository;

    public LoginController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String fazerLogin(@RequestParam String email,
                             @RequestParam String senha,
                             @RequestParam String cpf,
                             @RequestParam LocalDate dataNascimento,
                             Model model,
                             HttpSession session) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmailAndSenhaAndCpfAndDataNascimento(
                email, senha, cpf, dataNascimento
        );

        if (usuarioOptional.isEmpty()) {

            model.addAttribute("erro", "Dados inválidos. Verifique as informações e tente novamente.");

            model.addAttribute("email", email);
            model.addAttribute("cpf", cpf);
            model.addAttribute("dataNascimento", dataNascimento);

            return "login";
        }

        Usuario usuario = usuarioOptional.get();
        session.setAttribute("usuarioLogado", usuario);

        if (usuario.getTipoUsuario().equalsIgnoreCase("admin")) {
            return "redirect:/admin";
        } else if (usuario.getTipoUsuario().equalsIgnoreCase("professor")) {
            return "redirect:/notas";
        } else if (usuario.getTipoUsuario().equalsIgnoreCase("aluno")) {
            return "redirect:/boletim";
        }

        model.addAttribute("erro", "Tipo de usuário inválido.");
        return "login";
    }
    @GetMapping("/minha-conta")
    public String minhaConta(HttpSession session, Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);

        return "minha-conta";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }
}