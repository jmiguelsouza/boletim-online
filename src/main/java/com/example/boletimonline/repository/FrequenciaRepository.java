package com.example.boletimonline.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.boletimonline.model.Frequencia;



public interface FrequenciaRepository
        extends JpaRepository<Frequencia, Integer>{



    List<Frequencia> findByDisciplinaIdAndPeriodo(
            Integer disciplinaId,
            String periodo
    );


    Frequencia findByDisciplinaIdAndPeriodoAndMatriculaAluno(
            Integer disciplinaId,
            String periodo,
            String matriculaAluno
    );

    List<Frequencia> findByMatriculaAluno(
            String matriculaAluno
    );

    void deleteByDisciplinaId(Integer disciplinaId);
}