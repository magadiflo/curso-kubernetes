package com.magadiflo.dk.ms.usuarios.app.repositories;

import com.magadiflo.dk.ms.usuarios.app.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUsuarioRepository extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
