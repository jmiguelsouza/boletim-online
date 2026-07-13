package com.example.boletimonline.repository;

import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.boletimonline.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByCpf(String cpf);

    Optional<Usuario> findByEmailAndSenhaAndCpfAndDataNascimento(
            String email,
            String senha,
            String cpf,
            LocalDate dataNascimento
    );


    List<Usuario> findByTipoUsuario(String tipoUsuario);


    List<Usuario> findByPeriodoTurma(String periodoTurma);


    List<Usuario> findByTipoUsuarioAndPeriodoTurma(
            String tipoUsuario,
            String periodoTurma
    );


    List<Usuario> findByTipoUsuarioAndNomeContainingIgnoreCase(
            String tipoUsuario,
            String nome
    );

    List<Usuario> findByTipoUsuarioAndPeriodoTurmaAndNomeContainingIgnoreCase(
            String tipoUsuario,
            String periodoTurma,
            String nome
    );


    @Query("""
    SELECT u FROM Usuario u
    LEFT JOIN FETCH u.periodos
    WHERE u.tipoUsuario = :tipo
    """)
    List<Usuario> findProfessoresComPeriodos(
            @Param("tipo") String tipo
    );

}