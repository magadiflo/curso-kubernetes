package com.magadiflo.dk.ms.cursos.app.services.impl;

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

    public CursoServiceImpl(ICursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
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
}
