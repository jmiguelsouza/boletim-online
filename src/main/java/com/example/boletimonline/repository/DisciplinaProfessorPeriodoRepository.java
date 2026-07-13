package com.example.boletimonline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.boletimonline.model.DisciplinaProfessorPeriodo;

public interface DisciplinaProfessorPeriodoRepository
        extends JpaRepository<DisciplinaProfessorPeriodo, Long> {


    boolean existsByDisciplinaId(Long disciplinaId);

    boolean existsByDisciplinaIdAndProfessorMatriculaAndPeriodo(
            Long disciplinaId,
            String matricula,
            String periodo
    );

    boolean existsByDisciplinaIdAndPeriodo(
            Long disciplinaId,
            String periodo
    );

    List<DisciplinaProfessorPeriodo> findByDisciplinaIdAndProfessorMatricula(
            Long disciplinaId,
            String matricula
    );

    List<DisciplinaProfessorPeriodo> findByProfessorMatricula(String matricula);
    void deleteByDisciplinaId(Long disciplinaId);
}