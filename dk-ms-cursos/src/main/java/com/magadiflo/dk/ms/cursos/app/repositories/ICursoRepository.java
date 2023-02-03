package com.magadiflo.dk.ms.cursos.app.repositories;

import com.magadiflo.dk.ms.cursos.app.entity.Curso;
import org.springframework.data.repository.CrudRepository;

public interface ICursoRepository extends CrudRepository<Curso, Long> {
}
