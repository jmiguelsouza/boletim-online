package com.example.boletimonline.controller;


import java.util.List;
import java.util.Map;
import java.util.HashMap;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import com.example.boletimonline.model.Usuario;
import com.example.boletimonline.model.Avaliacao;
import com.example.boletimonline.model.Nota;


import com.example.boletimonline.repository.AvaliacaoRepository;
import com.example.boletimonline.repository.NotaRepository;
import com.example.boletimonline.repository.FrequenciaRepository;
import com.example.boletimonline.repository.MateriaRepository;
import com.example.boletimonline.model.Frequencia;


@Controller
public class AlunoController {


    private final AvaliacaoRepository avaliacaoRepository;
    private final NotaRepository notaRepository;
    private final FrequenciaRepository frequenciaRepository;
    private final MateriaRepository materiaRepository;



    public AlunoController(
            AvaliacaoRepository avaliacaoRepository,
            NotaRepository notaRepository,
            FrequenciaRepository frequenciaRepository,
            MateriaRepository materiaRepository
    ){

        this.avaliacaoRepository = avaliacaoRepository;
        this.notaRepository = notaRepository;
        this.frequenciaRepository = frequenciaRepository;
        this.materiaRepository = materiaRepository;

    }


    @GetMapping("/boletim")
    public String boletim(
            HttpSession session,
            Model model
    ){

        Usuario aluno =
                (Usuario) session.getAttribute("usuarioLogado");


        if(aluno == null){
            return "redirect:/login";
        }


        List<Avaliacao> avaliacoes =
                avaliacaoRepository.findAll();



        List<Nota> notas =
                notaRepository.findByMatriculaAluno(
                        aluno.getMatricula()
                );

        List<Frequencia> listaFrequencias =
                frequenciaRepository.findByMatriculaAluno(
                        aluno.getMatricula()
                );


        Map<Integer,Double> frequencias =
                new HashMap<>();

        for(Frequencia f : listaFrequencias){

            frequencias.put(
                    f.getDisciplinaId(),
                    f.getPercentual()
            );

        }

        Map<Integer,Double> medias = new HashMap<>();



        Map<Integer,Integer> qtdNotas =
                new HashMap<>();



        for(Nota n : notas){


            if(n.getNota()==null){
                continue;
            }


            Avaliacao avaliacao =
                    avaliacoes.stream()
                            .filter(a ->
                                    a.getId()
                                            .equals(n.getAvaliacaoId())
                            )
                            .findFirst()
                            .orElse(null);



            if(avaliacao == null){
                continue;
            }



            if(!avaliacao.getPeriodo()
                    .equals(aluno.getPeriodoTurma())){

                continue;

            }



            Integer disciplina =
                    avaliacao.getDisciplinaId();



            medias.put(
                    disciplina,
                    medias.getOrDefault(disciplina,0.0)
                            + n.getNota()
            );



            qtdNotas.put(
                    disciplina,
                    qtdNotas.getOrDefault(disciplina,0)
                            + 1
            );


        }



        for(Integer disciplina : medias.keySet()){


            medias.put(
                    disciplina,
                    medias.get(disciplina)
                            /
                            qtdNotas.get(disciplina)
            );


        }




        Map<Integer,String> nomesMaterias =
                new HashMap<>();


        for(Integer id : medias.keySet()){


            materiaRepository
                    .findById(id.longValue())
                    .ifPresent(m -> {

                        nomesMaterias.put(
                                id,
                                m.getNome()
                        );

                    });

        }



        Map<Integer,String> situacao =
                new HashMap<>();


        for(Integer disciplina : medias.keySet()){


            double media =
                    medias.get(disciplina);


            double frequencia =
                    frequencias.getOrDefault(
                            disciplina,
                            0.0
                    );


            if(media >= 60 && frequencia >= 75){

                situacao.put(
                        disciplina,
                        "Aprovado"
                );

            }else{

                situacao.put(
                        disciplina,
                        "Reprovado"
                );

            }

        }


        model.addAttribute(
                "aluno",
                aluno
        );


        model.addAttribute(
                "nomesMaterias",
                nomesMaterias
        );


        model.addAttribute(
                "medias",
                medias
        );

        model.addAttribute(
                "frequencias",
                frequencias
        );

        model.addAttribute(
                "situacao",
                situacao
        );

        return "boletim";

    }


}