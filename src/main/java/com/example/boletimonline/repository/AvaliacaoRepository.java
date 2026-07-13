package com.example.boletimonline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.boletimonline.model.Avaliacao;

public interface AvaliacaoRepository
        extends JpaRepository<Avaliacao, Integer> {

    List<Avaliacao> findByDisciplinaIdAndMatriculaAluno(
            Integer disciplinaId,
            String matriculaAluno
    );


    List<Avaliacao> findByDisciplinaId(
            Integer disciplinaId
    );

    List<Avaliacao> findByDisciplinaIdAndPeriodo(
            Integer disciplinaId,
            String periodo
    );

    boolean existsById(Integer id);
    void deleteByDisciplinaId(Integer disciplinaId);
    void deleteById(Integer id);
}