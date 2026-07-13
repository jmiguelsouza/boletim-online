package com.example.boletimonline.model;

import jakarta.persistence.*;

@Entity
@Table(name="avaliacao")
public class Avaliacao {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String matriculaAluno;


    private Integer disciplinaId;


    private String periodo;


    private String descricao;


    private Double nota;



    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getMatriculaAluno() {
        return matriculaAluno;
    }


    public void setMatriculaAluno(String matriculaAluno) {
        this.matriculaAluno = matriculaAluno;
    }


    public Integer getDisciplinaId() {
        return disciplinaId;
    }


    public void setDisciplinaId(Integer disciplinaId) {
        this.disciplinaId = disciplinaId;
    }


    public String getDescricao() {
        return descricao;
    }


    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public Double getNota() {
        return nota;
    }


    public void setNota(Double nota) {
        this.nota = nota;
    }


    public String getPeriodo() {
        return periodo;
    }


    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

}
