package com.example.boletimonline.model;

import jakarta.persistence.*;

@Entity
@Table(name="nota")
public class Nota {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name="avaliacao_id")
    private Integer avaliacaoId;


    @Column(name="matricula_aluno")
    private String matriculaAluno;


    private Double nota;


    private String descricao;



    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getAvaliacaoId() {
        return avaliacaoId;
    }


    public void setAvaliacaoId(Integer avaliacaoId) {
        this.avaliacaoId = avaliacaoId;
    }


    public String getMatriculaAluno() {
        return matriculaAluno;
    }


    public void setMatriculaAluno(String matriculaAluno) {
        this.matriculaAluno = matriculaAluno;
    }


    public Double getNota() {
        return nota;
    }


    public void setNota(Double nota) {
        this.nota = nota;
    }


    public String getDescricao() {
        return descricao;
    }


    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}