package com.magadiflo.dk.ms.usuarios.app.services;

import com.magadiflo.dk.ms.usuarios.app.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    List<Usuario> listar();

    Optional<Usuario> porId(Long id);

    Usuario guardar(Usuario usuario);

    void eliminar(Long id);

    List<Usuario> listarPorIds(Iterable<Long> ids);

    Optional<Usuario> porEmail(String email);

    boolean existePorEmail(String email);
}
