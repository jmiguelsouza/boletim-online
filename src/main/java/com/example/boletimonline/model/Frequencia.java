package com.example.boletimonline.model;


import jakarta.persistence.*;


@Entity
@Table(name="frequencia")
public class Frequencia {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name="matricula_aluno")
    private String matriculaAluno;


    @Column(name="disciplina_id")
    private Integer disciplinaId;


    private String periodo;


    @Column(name="total_aulas")
    private Integer totalAulas;


    private Integer presencas;


    private Integer faltas;



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



    public String getPeriodo() {
        return periodo;
    }


    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }



    public Integer getTotalAulas() {
        return totalAulas;
    }


    public void setTotalAulas(Integer totalAulas) {
        this.totalAulas = totalAulas;
    }



    public Integer getPresencas() {
        return presencas;
    }


    public void setPresencas(Integer presencas) {
        this.presencas = presencas;
    }



    public Integer getFaltas() {
        return faltas;
    }


    public void setFaltas(Integer faltas) {
        this.faltas = faltas;
    }



    public double getPercentual(){


        if(totalAulas == null || totalAulas == 0){

            return 0;

        }


        return (presencas * 100.0) / totalAulas;

    }


}