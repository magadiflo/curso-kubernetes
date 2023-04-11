package com.magadiflo.dk.ms.cursos.app.services;

import com.magadiflo.dk.ms.cursos.app.models.Usuario;
import com.magadiflo.dk.ms.cursos.app.models.entity.Curso;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Optional;

public interface ICursoService {

    //************ Lógica de negocio para persistencia CRUD BD
    List<Curso> listar();

    Optional<Curso> porId(Long id);

    Optional<Curso> porIdConUsuarios(Long id, String token);

    Curso guardar(Curso curso);

    void eliminar(Long id);

    void eliminarCursoUsuarioPorId(Long usuarioId);

    //************ Lógica de Negocio para obtener datos de otros microservicio
    Optional<Usuario> asignarUsuario(Usuario usuarioExistente, Long cursoId);

    Optional<Usuario> crearUsuario(Usuario usuarioNuevo, Long cursoId);

    Optional<Usuario> desAsignarUsuario(Usuario usuario, Long cursoId);
}
