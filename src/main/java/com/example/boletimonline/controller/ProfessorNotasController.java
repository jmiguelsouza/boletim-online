package com.example.boletimonline.controller;


import java.util.List;
import java.util.Map;
import java.util.HashMap;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.boletimonline.model.Avaliacao;
import com.example.boletimonline.model.Usuario;
import com.example.boletimonline.model.Nota;
import com.example.boletimonline.model.Frequencia;

import com.example.boletimonline.repository.AvaliacaoRepository;
import com.example.boletimonline.repository.UsuarioRepository;
import com.example.boletimonline.repository.DisciplinaProfessorPeriodoRepository;
import com.example.boletimonline.repository.NotaRepository;
import com.example.boletimonline.repository.FrequenciaRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class ProfessorNotasController {


    private final AvaliacaoRepository avaliacaoRepository;

    private final UsuarioRepository usuarioRepository;

    private final DisciplinaProfessorPeriodoRepository disciplinaProfessorPeriodoRepository;

    private final NotaRepository notaRepository;

    private final FrequenciaRepository frequenciaRepository;



    public ProfessorNotasController(
            AvaliacaoRepository avaliacaoRepository,
            UsuarioRepository usuarioRepository,
            DisciplinaProfessorPeriodoRepository disciplinaProfessorPeriodoRepository,
            NotaRepository notaRepository,
            FrequenciaRepository frequenciaRepository
    ){

        this.avaliacaoRepository = avaliacaoRepository;

        this.usuarioRepository = usuarioRepository;

        this.disciplinaProfessorPeriodoRepository =
                disciplinaProfessorPeriodoRepository;

        this.notaRepository = notaRepository;

        this.frequenciaRepository = frequenciaRepository;

    }




    @GetMapping("/professor/notas")
    public String abrirNotas(

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




        List<Avaliacao> avaliacoes =

                avaliacaoRepository
                        .findByDisciplinaIdAndPeriodo(
                                disciplinaId,
                                periodo
                        );




        List<Usuario> alunos =

                usuarioRepository
                        .findByTipoUsuarioAndPeriodoTurma(
                                "aluno",
                                periodo
                        );




        List<Integer> idsAvaliacoes =

                avaliacoes.stream()
                        .map(Avaliacao::getId)
                        .toList();




        List<Nota> notas =

                notaRepository
                        .findByAvaliacaoIdIn(idsAvaliacoes);




        List<Frequencia> frequencias =

                frequenciaRepository
                        .findByDisciplinaIdAndPeriodo(
                                disciplinaId,
                                periodo
                        );





        Map<String,Double> mapaFrequencia =
                new HashMap<>();



        for(Frequencia f : frequencias){


            mapaFrequencia.put(
                    f.getMatriculaAluno(),
                    f.getPercentual()
            );


        }





        Map<String,Double> medias =
                new HashMap<>();



        for(Usuario aluno : alunos){


            double total = 0;

            int quantidade = 0;



            for(Nota n : notas){


                if(n.getMatriculaAluno()
                        .equals(aluno.getMatricula())){


                    if(n.getNota()!=null){


                        total += n.getNota();

                        quantidade++;

                    }

                }

            }




            double media = quantidade > 0
                    ?
                    total / quantidade
                    :
                    0;




            medias.put(
                    aluno.getMatricula(),
                    media
            );


        }







        Map<String,String> situacao =
                new HashMap<>();



        for(Usuario aluno : alunos){



            double mediaAluno =
                    medias.getOrDefault(
                            aluno.getMatricula(),
                            0.0
                    );



            double frequenciaAluno =
                    mapaFrequencia.getOrDefault(
                            aluno.getMatricula(),
                            0.0
                    );




            if(mediaAluno >= 60 &&
                    frequenciaAluno >= 75){


                situacao.put(
                        aluno.getMatricula(),
                        "Aprovado"
                );


            }else{


                situacao.put(
                        aluno.getMatricula(),
                        "Reprovado"
                );


            }


        }






        Map<String,Double> mapaNotas =
                new HashMap<>();



        for(Nota n : notas){


            if(n.getNota()!=null){


                mapaNotas.put(
                        n.getAvaliacaoId()
                                + "-"
                                + n.getMatriculaAluno(),
                        n.getNota()
                );


            }


        }





        model.addAttribute(
                "mapaFrequencia",
                mapaFrequencia
        );


        model.addAttribute(
                "situacao",
                situacao
        );



        model.addAttribute(
                "medias",
                medias
        );


        model.addAttribute(
                "mapaNotas",
                mapaNotas
        );


        model.addAttribute(
                "frequencias",
                frequencias
        );


        model.addAttribute(
                "avaliacoes",
                avaliacoes
        );


        model.addAttribute(
                "alunos",
                alunos
        );


        model.addAttribute(
                "periodo",
                periodo
        );


        model.addAttribute(
                "disciplinaId",
                disciplinaId
        );


        model.addAttribute(
                "professor",
                professor
        );



        return "notas-professor";

    }

    @PostMapping("/professor/salvar-notas")
    public String salvarNotas(

            @RequestParam Integer disciplinaId,
            @RequestParam String periodo,

            @RequestParam List<Integer> avaliacaoId,
            @RequestParam List<String> matriculaAluno,
            @RequestParam List<Double> nota,

            HttpSession session

    ){

        Usuario professor =
                (Usuario) session.getAttribute("usuarioLogado");


        if(professor == null){

            return "redirect:/login";

        }



        for(int i=0; i<avaliacaoId.size(); i++){



            String valorRecebido = String.valueOf(nota.get(i));


            Double valor = null;


            if(valorRecebido != null &&
                    !valorRecebido.equals("") &&
                    !valorRecebido.equals("null")){

                valor = Double.parseDouble(valorRecebido);

            }




            Nota registro =
                    notaRepository
                            .findByAvaliacaoIdAndMatriculaAluno(
                                    avaliacaoId.get(i),
                                    matriculaAluno.get(i)
                            );



            if(registro == null){

                registro = new Nota();

            }


// se apagou a nota, remove do banco
            if(valor == null){

                if(registro.getId() != null){

                    notaRepository.delete(registro);

                }

                continue;

            }



            registro.setAvaliacaoId(
                    avaliacaoId.get(i)
            );


            registro.setMatriculaAluno(
                    matriculaAluno.get(i)
            );


            registro.setNota(
                    valor
            );


            notaRepository.save(registro);


        }



        return "redirect:/professor/notas?disciplinaId="
                + disciplinaId
                + "&periodo="
                + periodo;

    }

    @PostMapping("/professor/excluir-avaliacao")
    public String excluirAvaliacao(

            @RequestParam Integer avaliacaoId,
            @RequestParam Integer disciplinaId,
            @RequestParam String periodo,
            RedirectAttributes redirectAttributes

    ){


        boolean temNota =
                notaRepository.existsByAvaliacaoIdAndNotaIsNotNull(
                        avaliacaoId
                );



        if(temNota){


            redirectAttributes.addFlashAttribute(
                    "erro",
                    "Não é possível excluir. Essa avaliação possui notas lançadas."
            );


            return "redirect:/professor/notas?disciplinaId="
                    + disciplinaId
                    + "&periodo="
                    + periodo;


        }




        avaliacaoRepository.deleteById(
                avaliacaoId
        );



        redirectAttributes.addFlashAttribute(
                "sucesso",
                "Avaliação excluída com sucesso!"
        );



        return "redirect:/professor/notas?disciplinaId="
                + disciplinaId
                + "&periodo="
                + periodo;


    }



}