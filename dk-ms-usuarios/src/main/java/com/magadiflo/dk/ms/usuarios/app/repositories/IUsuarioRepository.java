package com.magadiflo.dk.ms.usuarios.app.repositories;

import com.magadiflo.dk.ms.usuarios.app.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioRepository extends CrudRepository<Usuario, Long> {
}
