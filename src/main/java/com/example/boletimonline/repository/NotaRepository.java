package com.example.boletimonline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.boletimonline.model.Nota;


public interface NotaRepository
        extends JpaRepository<Nota, Integer> {


    List<Nota> findByAvaliacaoId(
            Integer avaliacaoId
    );


    List<Nota> findByAvaliacaoIdIn(
            List<Integer> ids
    );


    List<Nota> findByMatriculaAluno(
            String matriculaAluno
    );

    Nota findByAvaliacaoIdAndMatriculaAluno(
            Integer avaliacaoId,
            String matriculaAluno
    );
    boolean existsByAvaliacaoIdAndNotaIsNotNull(
            Integer avaliacaoId
    );

    void deleteByAvaliacaoIdIn(List<Integer> ids);
}