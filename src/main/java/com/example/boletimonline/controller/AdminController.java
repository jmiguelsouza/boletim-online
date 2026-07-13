package com.example.boletimonline.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.boletimonline.model.Usuario;
import com.example.boletimonline.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.example.boletimonline.model.Materia;
import com.example.boletimonline.repository.MateriaRepository;
import com.example.boletimonline.model.DisciplinaProfessorPeriodo;
import com.example.boletimonline.repository.DisciplinaProfessorPeriodoRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.boletimonline.repository.AvaliacaoRepository;
import com.example.boletimonline.repository.NotaRepository;
import com.example.boletimonline.repository.FrequenciaRepository;
import com.example.boletimonline.model.Avaliacao;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class AdminController {

    private final UsuarioRepository usuarioRepository;
    private final MateriaRepository materiaRepository;
    private final DisciplinaProfessorPeriodoRepository disciplinaProfessorPeriodoRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final NotaRepository notaRepository;
    private final FrequenciaRepository frequenciaRepository;

    public AdminController(
            UsuarioRepository usuarioRepository,
            MateriaRepository materiaRepository,
            DisciplinaProfessorPeriodoRepository disciplinaProfessorPeriodoRepository,
            AvaliacaoRepository avaliacaoRepository,
            NotaRepository notaRepository,
            FrequenciaRepository frequenciaRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.materiaRepository = materiaRepository;
        this.disciplinaProfessorPeriodoRepository = disciplinaProfessorPeriodoRepository;
        this.avaliacaoRepository = avaliacaoRepository;
        this.notaRepository = notaRepository;
        this.frequenciaRepository = frequenciaRepository;
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/admin/alunos")
    public String listarAlunos(@RequestParam(required = false) String periodo, Model model) {

        List<Usuario> alunos;

        if (periodo != null && !periodo.isEmpty()) {
            alunos = usuarioRepository.findByTipoUsuarioAndPeriodoTurma("aluno", periodo);
        } else {
            alunos = usuarioRepository.findByTipoUsuario("aluno");
        }

        model.addAttribute("alunos", alunos);
        model.addAttribute("periodoSelecionado", periodo);

        return "listaaluno";
    }

    @GetMapping("/admin/alunos/excluir/{matricula}")
    public String excluirAluno(@PathVariable String matricula) {
        usuarioRepository.deleteById(matricula);
        return "redirect:/admin/alunos";
    }

    @GetMapping("/admin/professores")
    public String listarProfessores(Model model) {
        List<Usuario> professores = usuarioRepository.findByTipoUsuario("professor");
        model.addAttribute("professores", professores);
        return "listaprofessor";
    }

    @GetMapping("/admin/professores/excluir/{matricula}")
    public String excluirProfessor(@PathVariable String matricula) {
        usuarioRepository.deleteById(matricula);
        return "redirect:/admin/professores";
    }

    @GetMapping("/admin/excluir-relacao/{id}")
    public String excluirRelacao(@PathVariable Long id, RedirectAttributes redirectAttributes){

        disciplinaProfessorPeriodoRepository.deleteById(id);

        redirectAttributes.addFlashAttribute(
                "sucesso",
                "Relação excluída com sucesso! ✅"
        );

        return "redirect:/admin/relacoes";
    }

    @Transactional
    @GetMapping("/admin/disciplina/excluir/{id}")
    public String excluirDisciplina(@PathVariable Long id){

        // Apaga frequências
        frequenciaRepository.deleteByDisciplinaId(id.intValue());

        // Busca todas as avaliações da disciplina
        List<Avaliacao> avaliacoes =
                avaliacaoRepository.findByDisciplinaId(id.intValue());

        // Pega os IDs das avaliações
        List<Integer> idsAvaliacoes =
                avaliacoes.stream()
                        .map(Avaliacao::getId)
                        .toList();

        // Apaga todas as notas dessas avaliações
        if(!idsAvaliacoes.isEmpty()){
            notaRepository.deleteByAvaliacaoIdIn(idsAvaliacoes);
        }

        // Apaga as avaliações
        avaliacaoRepository.deleteByDisciplinaId(id.intValue());

        // Apaga os vínculos professor/período
        disciplinaProfessorPeriodoRepository.deleteByDisciplinaId(id);

        // Finalmente apaga a disciplina
        materiaRepository.deleteById(id);

        return "redirect:/admin/disciplina?sucesso=true";
    }

    @GetMapping("/admin/usuario/toggle-status/{matricula}")
    public String alternarStatus(@PathVariable String matricula) {

        Usuario usuario = usuarioRepository.findById(matricula).orElse(null);

        if (usuario != null) {

            if ("suspenso".equalsIgnoreCase(usuario.getStatus())) {
                usuario.setStatus("ativo");
            } else {
                usuario.setStatus("suspenso");
            }

            usuarioRepository.save(usuario);
        }

        return "redirect:/admin";
    }

    @GetMapping("/admin/disciplina")
    public String cadastroDisciplina(Model model){

        List<Materia> disciplinas = materiaRepository.findAll();

        model.addAttribute("disciplinas", disciplinas);

        return "disciplina";
    }

    @GetMapping("/admin/vincular-disciplina")
    public String vincularDisciplina(
            @RequestParam(required = false) String sucesso,
            Model model
    ){
        List<Materia> materias =
                materiaRepository.findAll();
        List<Usuario> professores =
                usuarioRepository.findProfessoresComPeriodos("professor");
        model.addAttribute("materias", materias);

        model.addAttribute("professores", professores);

        if(sucesso != null){
            model.addAttribute("sucesso", true);
        }
        return "vincular-disciplina";
    }
    @PostMapping("/admin/salvar-vinculo-disciplina")
    public String salvarVinculo(

            @RequestParam Long disciplinaId,

            @RequestParam String professorId,

            @RequestParam List<String> periodos,

            Model model

    ){
        Materia materia =
                materiaRepository
                        .findById(disciplinaId)
                        .orElseThrow();

        Usuario professor =
                usuarioRepository
                        .findById(professorId)
                        .orElseThrow();

        // PRIMEIRO VERIFICA TUDO
        for(String periodo : periodos){

            boolean materiaJaExisteNaTurma =
                    disciplinaProfessorPeriodoRepository
                            .existsByDisciplinaIdAndPeriodo(
                                    disciplinaId,
                                    periodo
                            );

            boolean mesmoProfessorRepetido =
                    disciplinaProfessorPeriodoRepository
                            .existsByDisciplinaIdAndProfessorMatriculaAndPeriodo(
                                    disciplinaId,
                                    professor.getMatricula(),
                                    periodo
                            );

            if(materiaJaExisteNaTurma || mesmoProfessorRepetido){

                model.addAttribute(
                        "erro",
                        "Não foi possível criar o vínculo. Essa matéria já possui um professor nessa turma!"
                );

                model.addAttribute(
                        "materias",
                        materiaRepository.findAll()
                );

                model.addAttribute(
                        "professores",
                        usuarioRepository.findByTipoUsuario("professor")
                );

                return "vincular-disciplina";

            }
        }

        // SE PASSOU, SALVA

        for(String periodo : periodos){

            DisciplinaProfessorPeriodo vinculo =
                    new DisciplinaProfessorPeriodo();

            vinculo.setDisciplina(materia);

            vinculo.setProfessor(professor);

            vinculo.setPeriodo(periodo);

            disciplinaProfessorPeriodoRepository.save(vinculo);

        }
        return "redirect:/admin/vincular-disciplina?sucesso=true";

    }

    @PostMapping("/admin/salvar-disciplina")
    public String salvarDisciplina(
            @ModelAttribute Materia materia
    ){

        materiaRepository.save(materia);

        return "redirect:/admin/disciplina";
    }

    @GetMapping("/admin/relacoes")
    public String listarRelacoes(Model model){

        List<DisciplinaProfessorPeriodo> relacoes =
                disciplinaProfessorPeriodoRepository.findAll();

        model.addAttribute("relacoes", relacoes);
        return "relacoes";
    }


}
