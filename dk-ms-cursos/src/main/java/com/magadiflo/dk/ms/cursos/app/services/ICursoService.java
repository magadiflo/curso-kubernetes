package com.magadiflo.dk.ms.cursos.app.services;

import com.magadiflo.dk.ms.cursos.app.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface ICursoService {
    List<Curso> listar();

    Optional<Curso> porId(Long id);

    Curso guardar(Curso curso);

    void eliminar(Long id);
}
