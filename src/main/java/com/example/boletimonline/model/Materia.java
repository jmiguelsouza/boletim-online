package com.example.boletimonline.model;

import jakarta.persistence.*;

@Entity
@Table(name = "disciplina")
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "disciplina_id")
    private Long id;
    private String nome;
    private boolean ativa = true;

    public Materia() {

    }

    public Materia(String nome) {

        this.nome = nome;
        this.ativa = true;

    }

    public Long getId() {

        return id;

    }

    public void setId(Long id) {

        this.id = id;

    }

    public String getNome() {

        return nome;

    }

    public void setNome(String nome) {

        this.nome = nome;

    }

    public boolean isAtiva() {

        return ativa;

    }

    public void setAtiva(boolean ativa) {

        this.ativa = ativa;

    }
}
