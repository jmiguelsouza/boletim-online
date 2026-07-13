package com.example.boletimonline.controller;

import java.util.Optional;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.boletimonline.model.Usuario;
import com.example.boletimonline.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import com.example.boletimonline.repository.ProfessorPeriodoRepository;
import com.example.boletimonline.model.ProfessorPeriodo;



    @Controller
    public class UsuarioController {

        private final UsuarioRepository usuarioRepository;
        private final ProfessorPeriodoRepository professorPeriodoRepository;

        public UsuarioController(
                UsuarioRepository usuarioRepository,
                ProfessorPeriodoRepository professorPeriodoRepository) {

            this.usuarioRepository = usuarioRepository;
            this.professorPeriodoRepository = professorPeriodoRepository;
        }

        @GetMapping("/usuarios")
        @ResponseBody
        public List<Usuario> listarUsuarios() {
            return usuarioRepository.findAll();
        }

        @GetMapping("/cadastro-usuario")
        public String mostrarFormularioCadastro(
                @RequestParam(required = false) String sucesso,
                @RequestParam(required = false) String erro,

                @RequestParam(required = false) String nome,
                @RequestParam(required = false) String email,
                @RequestParam(required = false) String senha,
                @RequestParam(required = false) String tipoUsuario,
                @RequestParam(required = false) String cpf,
                @RequestParam(required = false) String dataNascimento,
                @RequestParam(required = false) String periodoTurma,

                Model model) {

            if (sucesso != null) {
                model.addAttribute("sucesso", true);
            }

            if ("existe".equals(erro)) {
                model.addAttribute("erro", "Este email já está cadastrado.");
            }

            if ("cpf".equals(erro)) {
                model.addAttribute("erro", "Este CPF já está cadastrado.");
            }

            model.addAttribute("nome", nome);
            model.addAttribute("email", email);
            model.addAttribute("senha", senha);
            model.addAttribute("tipoUsuario", tipoUsuario);
            model.addAttribute("cpf", cpf);
            model.addAttribute("dataNascimento", dataNascimento);
            model.addAttribute("periodoTurma", periodoTurma);

            return "cadastro-usuario";
        }

        @PostMapping("/salvar-usuario")
        public String salvarUsuario(@ModelAttribute Usuario usuario,
                                    @RequestParam(name = "periodosProfessor", required = false) List<String> periodos) {

            System.out.println("ENTROU NO METODO");
            System.out.println(periodos);

            Optional<Usuario> usuarioExistente =
                    usuarioRepository.findByEmail(usuario.getEmail());

            if (usuarioExistente.isPresent()) {
                return "redirect:/cadastro-usuario?erro=existe"
                        + "&nome=" + usuario.getNome()
                        + "&email=" + usuario.getEmail()
                        + "&senha=" + usuario.getSenha()
                        + "&tipoUsuario=" + usuario.getTipoUsuario()
                        + "&cpf=" + usuario.getCpf()
                        + "&dataNascimento=" + usuario.getDataNascimento()
                        + "&periodoTurma=" + usuario.getPeriodoTurma();
            }

            Optional<Usuario> cpfExistente =
                    usuarioRepository.findByCpf(usuario.getCpf());

            if (cpfExistente.isPresent()) {
                return "redirect:/cadastro-usuario?erro=cpf"
                        + "&nome=" + usuario.getNome()
                        + "&email=" + usuario.getEmail()
                        + "&senha=" + usuario.getSenha()
                        + "&tipoUsuario=" + usuario.getTipoUsuario()
                        + "&cpf=" + usuario.getCpf()
                        + "&dataNascimento=" + usuario.getDataNascimento()
                        + "&periodoTurma=" + usuario.getPeriodoTurma();
            }

            usuario.setStatus("ATIVO");
            usuario.setMatricula(gerarMatricula());

            if (usuario.getTipoUsuario()
                    .equalsIgnoreCase("professor")) {

                usuarioRepository.save(usuario);

                if (periodos != null) {

                    for (String periodo : periodos) {

                        ProfessorPeriodo professorPeriodo =
                                new ProfessorPeriodo();
                        professorPeriodo.setProfessor(usuario);

                        professorPeriodo.setPeriodo(periodo);
                        professorPeriodoRepository
                                .save(professorPeriodo);
                    }
                }

                return "redirect:/cadastro-usuario?sucesso";
            }

            usuarioRepository.save(usuario);

            return "redirect:/cadastro-usuario?sucesso";
        }

        @GetMapping("/listaprofessor")
        public String listarProfessores(@RequestParam(required = false) String nome,
                                        Model model) {
            List<Usuario> professores;

            if (nome != null && !nome.trim().isEmpty()) {

                professores = usuarioRepository
                        .findByTipoUsuarioAndNomeContainingIgnoreCase(
                                "professor",
                                nome.trim()
                        );

            } else {

                professores = usuarioRepository
                        .findByTipoUsuario("professor");
            }

            model.addAttribute("professores", professores);
            model.addAttribute("nome", nome);
            return "listaprofessor";
        }

        private String gerarMatricula() {

            int ano = java.time.LocalDate.now().getYear();

            List<Usuario> usuarios = usuarioRepository.findAll();

            long maior = usuarios.stream()
                    .filter(u -> u.getMatricula() != null)
                    .filter(u -> u.getMatricula().startsWith(String.valueOf(ano)))
                    .mapToLong(u ->
                            Long.parseLong(
                                    u.getMatricula().substring(4)
                            )
                    )
                    .max()
                    .orElse(0);

            long proximo = maior + 1;

            return String.format("%d%04d", ano, proximo);
        }

        @GetMapping("/editar-usuario/{matricula}")
        public String editarUsuario(
                @PathVariable String matricula,
                Model model,
                HttpSession session
        ) {

            Usuario logado =
                    (Usuario) session.getAttribute("usuarioLogado");

            if(logado != null &&
                    logado.getTipoUsuario()
                            .equalsIgnoreCase("professor")){


                return "redirect:/minha-conta";

            }

            Optional<Usuario> usuario =
                    usuarioRepository.findById(matricula);

            if (usuario.isPresent()) {

                Usuario u = usuario.get();

                model.addAttribute("usuario", u);

                List<ProfessorPeriodo> periodos =
                        professorPeriodoRepository
                                .findByProfessor(u);
                model.addAttribute(
                        "periodosProfessor",
                        periodos
                );

                return "editar-usuario";
            }
            return "redirect:/listaprofessor";
        }

        @PostMapping("/atualizar-usuario")
        public String atualizarUsuario(
                @ModelAttribute Usuario usuario,
                @RequestParam(name="periodosProfessor", required=false)
                List<String> periodos,
                HttpSession session) {


            Usuario u =
                    usuarioRepository.findById(usuario.getMatricula())
                            .orElseThrow();


            u.setNome(usuario.getNome());
            u.setEmail(usuario.getEmail());
            u.setSenha(usuario.getSenha());
            u.setCpf(usuario.getCpf());
            u.setDataNascimento(usuario.getDataNascimento());
            u.setTipoUsuario(usuario.getTipoUsuario());

            if(!u.getTipoUsuario()
                    .equalsIgnoreCase("professor")) {

                u.setPeriodoTurma(usuario.getPeriodoTurma());
            }

            usuarioRepository.save(u);

            if(u.getTipoUsuario()
                    .equalsIgnoreCase("professor")) {

                List<ProfessorPeriodo> antigos =
                        professorPeriodoRepository
                                .findByProfessor(u);

                professorPeriodoRepository
                        .deleteAll(antigos);

                if(periodos != null) {

                    for(String p : periodos) {


                        ProfessorPeriodo pp =
                                new ProfessorPeriodo();

                        pp.setProfessor(u);
                        pp.setPeriodo(p);


                        professorPeriodoRepository.save(pp);
                    }
                }
            }

            Usuario logado =
                    (Usuario) session.getAttribute("usuarioLogado");


            if(logado != null &&
                    logado.getMatricula()
                            .equals(u.getMatricula())) {

                session.setAttribute(
                        "usuarioLogado", u);
            }

            if(u.getTipoUsuario()
                    .equalsIgnoreCase("admin")) {

                return "redirect:/minha-conta";
            }


            if(u.getTipoUsuario()
                    .equalsIgnoreCase("aluno")) {

                return "redirect:/admin/alunos";
            }
            return "redirect:/listaprofessor";
        }

    }