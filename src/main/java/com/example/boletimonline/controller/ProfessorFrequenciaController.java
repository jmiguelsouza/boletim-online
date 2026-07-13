package com.example.boletimonline.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import com.example.boletimonline.model.Usuario;
import com.example.boletimonline.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.boletimonline.model.Frequencia;
import com.example.boletimonline.repository.FrequenciaRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;


@Controller
public class ProfessorFrequenciaController {

    private final UsuarioRepository usuarioRepository;
    private final FrequenciaRepository frequenciaRepository;


    public ProfessorFrequenciaController(
            UsuarioRepository usuarioRepository,
            FrequenciaRepository frequenciaRepository
    ){

        this.usuarioRepository = usuarioRepository;
        this.frequenciaRepository = frequenciaRepository;

    }



    @GetMapping("/professor/frequencia")
    public String abrirFrequencia(

            @RequestParam Integer disciplinaId,
            @RequestParam String periodo,

            HttpSession session,

            Model model

    ){


        Usuario professor =
                (Usuario) session.getAttribute("usuarioLogado");


        if(professor == null){

            return "redirect:/login";

        }



        List<Usuario> alunos =
                usuarioRepository
                        .findByTipoUsuarioAndPeriodoTurma(
                                "aluno",
                                periodo
                        );

        List<Frequencia> frequencias =
                frequenciaRepository
                        .findByDisciplinaIdAndPeriodo(
                                disciplinaId,
                                periodo
                        );


        java.util.Map<String,Integer> mapaPresencas =
                new java.util.HashMap<>();

        Integer totalAulas = 0;

        for(Frequencia f : frequencias){


            mapaPresencas.put(
                    f.getMatriculaAluno(),
                    f.getPresencas()
            );

            totalAulas = f.getTotalAulas();

        }

        model.addAttribute(
                "alunos",
                alunos
        );

        model.addAttribute(
                "mapaPresencas",
                mapaPresencas
        );


        model.addAttribute(
                "totalAulas",
                totalAulas
        );

        model.addAttribute(
                "disciplinaId",
                disciplinaId
        );


        model.addAttribute(
                "periodo",
                periodo
        );


        model.addAttribute(
                "professor",
                professor
        );



        return "frequencia-professor";


    }

    @PostMapping("/professor/salvar-frequencia")
    public String salvarFrequencia(

            @RequestParam Integer disciplinaId,

            @RequestParam String periodo,

            @RequestParam Integer totalAulas,

            @RequestParam List<Integer> presencas,

            @RequestParam List<String> matriculaAluno,

            RedirectAttributes redirectAttributes

    ){
        if(totalAulas < 0){

            redirectAttributes.addFlashAttribute(
                    "erro",
                    "A quantidade de aulas não pode ser negativa."
            );


            return "redirect:/professor/frequencia?disciplinaId="
                    + disciplinaId
                    + "&periodo="
                    + periodo;

        }


        for(Integer p : presencas){

            if(p > totalAulas){

                redirectAttributes.addFlashAttribute(
                        "erro",
                        "A presença do aluno não pode ser maior que o total de aulas."
                );


                return "redirect:/professor/frequencia?disciplinaId="
                        + disciplinaId
                        + "&periodo="
                        + periodo;

            }

        }

        for(int i = 0; i < matriculaAluno.size(); i++){


            String matricula =
                    matriculaAluno.get(i);


            Integer presente =
                    presencas.get(i);



            Frequencia frequencia =
                    frequenciaRepository
                            .findByDisciplinaIdAndPeriodoAndMatriculaAluno(
                                    disciplinaId,
                                    periodo,
                                    matricula
                            );



            if(frequencia == null){

                frequencia = new Frequencia();

            }



            frequencia.setDisciplinaId(
                    disciplinaId
            );


            frequencia.setPeriodo(
                    periodo
            );


            frequencia.setMatriculaAluno(
                    matricula
            );


            frequencia.setTotalAulas(
                    totalAulas
            );


            frequencia.setPresencas(
                    presente
            );


            frequencia.setFaltas(
                    totalAulas - presente
            );



            frequenciaRepository.save(frequencia);



        }



        return "redirect:/professor/notas?disciplinaId="
                + disciplinaId
                + "&periodo="
                + periodo;


    }


}