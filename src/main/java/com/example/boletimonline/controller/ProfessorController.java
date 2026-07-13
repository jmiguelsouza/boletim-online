package com.example.boletimonline.controller;


import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import com.example.boletimonline.model.Usuario;
import com.example.boletimonline.model.DisciplinaProfessorPeriodo;
import com.example.boletimonline.repository.DisciplinaProfessorPeriodoRepository;



@Controller
public class ProfessorController {


    private final DisciplinaProfessorPeriodoRepository repository;



    public ProfessorController(
            DisciplinaProfessorPeriodoRepository repository
    ){

        this.repository = repository;

    }





    @GetMapping("/notas")
    public String notas(
            HttpSession session,
            Model model
    ){


        Usuario professor =
                (Usuario) session.getAttribute("usuarioLogado");



        if(professor == null){

            return "redirect:/login";

        }




        List<DisciplinaProfessorPeriodo> relacoes =

                repository.findByProfessorMatricula(
                        professor.getMatricula()
                );




        model.addAttribute(
                "relacoes",
                relacoes
        );



        model.addAttribute(
                "professor",
                professor
        );




        return "notas";

    }
    @GetMapping("/notas/{id}/{periodo}")
    public String abrirMateria(
            @PathVariable Integer id,
            @PathVariable String periodo,
            HttpSession session,
            Model model
    ){

        Usuario professor =
                (Usuario) session.getAttribute("usuarioLogado");


        if(professor == null){

            return "redirect:/login";

        }


        model.addAttribute(
                "disciplinaId",
                id
        );


        model.addAttribute(
                "periodo",
                periodo
        );


        model.addAttribute(
                "professor",
                professor
        );


        return "lancar-nota";

    }

}