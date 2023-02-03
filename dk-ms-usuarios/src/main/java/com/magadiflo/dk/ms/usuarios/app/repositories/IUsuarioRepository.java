package com.magadiflo.dk.ms.usuarios.app.repositories;

import com.magadiflo.dk.ms.usuarios.app.models.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUsuarioRepository extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    @Query("SELECT u FROM Usuario AS u WHERE u.email = ?1")
    Optional<Usuario> encontrarPorEmail(String email);

    boolean existsByEmail(String email);
}
