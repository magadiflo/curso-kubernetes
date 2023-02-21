package com.magadiflo.dk.ms.cursos.app.services;

import com.magadiflo.dk.ms.cursos.app.models.Usuario;
import com.magadiflo.dk.ms.cursos.app.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface ICursoService {

    //************ Lógica de negocio para persistencia CRUD BD
    List<Curso> listar();

    Optional<Curso> porId(Long id);

    Optional<Curso> porIdConUsuarios(Long id);

    Curso guardar(Curso curso);

    void eliminar(Long id);

    //************ Lógica de Negocio para obtener datos de otros microservicio
    Optional<Usuario> asignarUsuario(Usuario usuarioExistente, Long cursoId);

    Optional<Usuario> crearUsuario(Usuario usuarioNuevo, Long cursoId);

    Optional<Usuario> desAsignarUsuario(Usuario usuario, Long cursoId);
}
