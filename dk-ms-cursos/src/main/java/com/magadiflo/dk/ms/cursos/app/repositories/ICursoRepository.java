package com.magadiflo.dk.ms.cursos.app.repositories;

import com.magadiflo.dk.ms.cursos.app.models.entity.Curso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ICursoRepository extends CrudRepository<Curso, Long> {
    /**
     * Típicamente, la anotación @Query es para realizar consultas,
     * no es tanto para delete, updated o insert; es decir, sí se pueden
     * hacer dichas consultas, pero necesitamos complementarla con la
     * anotación @Modifying para que realice la modificación, el cambio
     * en la tabla y no sea como una simple consulta.
     */
    @Modifying
    @Query("DELETE FROM CursoUsuario AS cu WHERE cu.usuarioId = ?1")
    void eliminarCursoUsuarioPorId(Long usuarioId);

}
