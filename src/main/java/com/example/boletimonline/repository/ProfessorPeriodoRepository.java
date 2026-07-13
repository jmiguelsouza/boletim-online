package com.example.boletimonline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.boletimonline.model.ProfessorPeriodo;
import com.example.boletimonline.model.Usuario;

public interface ProfessorPeriodoRepository
        extends JpaRepository<ProfessorPeriodo, Long> {


    List<ProfessorPeriodo> findByProfessor(Usuario professor);

}