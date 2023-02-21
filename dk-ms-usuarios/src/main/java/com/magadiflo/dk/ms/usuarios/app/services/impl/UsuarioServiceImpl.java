package com.magadiflo.dk.ms.usuarios.app.services.impl;

import com.magadiflo.dk.ms.usuarios.app.clients.ICursoClientFeign;
import com.magadiflo.dk.ms.usuarios.app.models.entity.Usuario;
import com.magadiflo.dk.ms.usuarios.app.repositories.IUsuarioRepository;
import com.magadiflo.dk.ms.usuarios.app.services.IUsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final ICursoClientFeign cursoClientFeign;

    public UsuarioServiceImpl(IUsuarioRepository usuarioRepository, ICursoClientFeign cursoClientFeign) {
        this.usuarioRepository = usuarioRepository;
        this.cursoClientFeign = cursoClientFeign;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return (List<Usuario>) this.usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> porId(Long id) {
        return this.usuarioRepository.findById(id);
    }

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        return this.usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        this.usuarioRepository.deleteById(id);
        this.cursoClientFeign.eliminarCursoUsuarioPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarPorIds(Iterable<Long> ids) {
        return (List<Usuario>) this.usuarioRepository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> porEmail(String email) {
        return this.usuarioRepository.encontrarPorEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorEmail(String email) {
        return this.usuarioRepository.existsByEmail(email);
    }
}
