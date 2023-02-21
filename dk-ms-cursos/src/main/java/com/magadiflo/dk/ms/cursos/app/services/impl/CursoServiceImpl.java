package com.magadiflo.dk.ms.cursos.app.services.impl;

import com.magadiflo.dk.ms.cursos.app.clients.IUsuarioClientFeign;
import com.magadiflo.dk.ms.cursos.app.models.Usuario;
import com.magadiflo.dk.ms.cursos.app.models.entity.Curso;
import com.magadiflo.dk.ms.cursos.app.models.entity.CursoUsuario;
import com.magadiflo.dk.ms.cursos.app.repositories.ICursoRepository;
import com.magadiflo.dk.ms.cursos.app.services.ICursoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements ICursoService {

    private final ICursoRepository cursoRepository;
    private final IUsuarioClientFeign usuarioClientFeign;

    public CursoServiceImpl(ICursoRepository cursoRepository, IUsuarioClientFeign usuarioClientFeign) {
        this.cursoRepository = cursoRepository;
        this.usuarioClientFeign = usuarioClientFeign;
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
    @Transactional(readOnly = true)
    public Optional<Curso> porIdConUsuarios(Long id) {
        Optional<Curso> cursoOptional = this.cursoRepository.findById(id);
        if (cursoOptional.isPresent()) {
            Curso curso = cursoOptional.get();
            if (!curso.getCursoUsuarios().isEmpty()) {
                List<Long> ids = curso.getCursoUsuarios().stream()
                        .map(CursoUsuario::getUsuarioId)
                        .toList();
                List<Usuario> usuarios = this.usuarioClientFeign.obtenerAlumnosPorCurso(ids);
                curso.setUsuarios(usuarios);
            }
            return Optional.of(curso);
        }
        return Optional.empty();
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
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuarioExistente, Long cursoId) {
        Optional<Curso> cursoOptional = this.cursoRepository.findById(cursoId);
        if (cursoOptional.isPresent()) {
            Usuario usuarioMs = this.usuarioClientFeign.detalle(usuarioExistente.getId());

            Curso curso = cursoOptional.get();

            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMs.getId());

            curso.addCursoUsuario(cursoUsuario);

            this.cursoRepository.save(curso);

            return Optional.of(usuarioMs);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuarioNuevo, Long cursoId) {
        Optional<Curso> cursoOptional = this.cursoRepository.findById(cursoId);
        if (cursoOptional.isPresent()) {
            Usuario usuarioMs = this.usuarioClientFeign.guardar(usuarioNuevo);

            Curso curso = cursoOptional.get();

            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMs.getId());

            curso.addCursoUsuario(cursoUsuario);

            this.cursoRepository.save(curso);

            return Optional.of(usuarioMs);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> desAsignarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> cursoOptional = this.cursoRepository.findById(cursoId);
        if (cursoOptional.isPresent()) {
            Usuario usuarioMs = this.usuarioClientFeign.detalle(usuario.getId());

            Curso curso = cursoOptional.get();

            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMs.getId());

            curso.removeCursoUsuario(cursoUsuario);

            this.cursoRepository.save(curso);

            return Optional.of(usuarioMs);
        }
        return Optional.empty();
    }
}
