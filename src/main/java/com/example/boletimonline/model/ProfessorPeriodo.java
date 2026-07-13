package com.example.boletimonline.model;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "professor_periodo")
public class ProfessorPeriodo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Usuario professor;

    private String periodo;


    public ProfessorPeriodo() {

    }

    public Long getId() {
        return id;
    }


    public Usuario getProfessor() {
        return professor;
    }


    public void setProfessor(Usuario professor) {
        this.professor = professor;
    }


    public String getPeriodo() {
        return periodo;
    }


    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}