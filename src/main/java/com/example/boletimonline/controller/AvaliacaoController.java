package com.example.boletimonline.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.boletimonline.model.Avaliacao;
import com.example.boletimonline.repository.AvaliacaoRepository;


@Controller
public class AvaliacaoController {


    private final AvaliacaoRepository avaliacaoRepository;


    public AvaliacaoController(
            AvaliacaoRepository avaliacaoRepository
    ){

        this.avaliacaoRepository = avaliacaoRepository;

    }



    @PostMapping("/professor/criar-avaliacao")
    public String criarAvaliacao(

            @RequestParam Integer disciplinaId,
            @RequestParam String periodo,
            @RequestParam String descricao

    ){


        Avaliacao avaliacao = new Avaliacao();


        avaliacao.setDisciplinaId(disciplinaId);

        avaliacao.setDescricao(descricao);

        avaliacao.setPeriodo(periodo);

        avaliacaoRepository.save(avaliacao);



        return "redirect:/professor/notas?disciplinaId="
                + disciplinaId
                + "&periodo="
                + periodo;

    }

}