package com.magadiflo.dk.ms.cursos.app.services.impl;

import com.magadiflo.dk.ms.cursos.app.clients.IUsuarioClientFeign;
import com.magadiflo.dk.ms.cursos.app.models.Usuario;
import com.magadiflo.dk.ms.cursos.app.models.entity.Curso;
import com.magadiflo.dk.ms.cursos.app.repositories.ICursoRepository;
import com.magadiflo.dk.ms.cursos.app.services.ICursoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements ICursoService {

    private final ICursoRepository cursoRepository;
    private final IUsuarioClientFeign clientFeign;

    public CursoServiceImpl(ICursoRepository cursoRepository, IUsuarioClientFeign clientFeign) {
        this.cursoRepository = cursoRepository;
        this.clientFeign = clientFeign;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return (List<Curso>) this.cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porId(Long id) {
        return this.cursoRepository.findById(id);
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return this.cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        this.cursoRepository.deleteById(id);
    }

    @Override
    public Optional<Usuario> asignarUsuario(Usuario usuarioExistente, Long cursoId) {
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> crearUsuario(Usuario usuarioNuevo, Long cursoId) {
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> desAsignarUsuario(Usuario usuario, Long cursoId) {
        return Optional.empty();
    }
}
