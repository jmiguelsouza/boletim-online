package com.example.boletimonline.model;

import jakarta.persistence.*;

@Entity
@Table(name="disciplina_professor_periodo")
public class DisciplinaProfessorPeriodo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name="disciplina_id")
    private Materia disciplina;


    @ManyToOne
    @JoinColumn(name="professor_id")
    private Usuario professor;

    private String periodo;

    public DisciplinaProfessorPeriodo(){

    }
    public Long getId() {
        return id;
    }
    public Materia getDisciplina() {
        return disciplina;
    }
    public void setDisciplina(Materia disciplina) {
        this.disciplina = disciplina;
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

