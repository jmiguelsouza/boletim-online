package com.example.boletimonline.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import jakarta.persistence.OneToMany;
import java.util.List;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    private String matricula;

    @OneToMany(
            mappedBy = "professor",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProfessorPeriodo> periodos =
            new java.util.ArrayList<>();

    private String nome;
    private String email;
    private String senha;

    @Column(name = "tipo_usuario")
    private String tipoUsuario;

    private String cpf;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "periodo_turma")
    private String periodoTurma;

    private String status;

    public Usuario() {
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getPeriodoTurma() {
        return periodoTurma;
    }

    public void setPeriodoTurma(String periodoTurma) {
        this.periodoTurma = periodoTurma;
    }

    public String getStatus() {
        return status;
    }

    public List<ProfessorPeriodo> getPeriodos() {
        return periodos;
    }


    public void setPeriodos(List<ProfessorPeriodo> periodos) {
        this.periodos = periodos;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}