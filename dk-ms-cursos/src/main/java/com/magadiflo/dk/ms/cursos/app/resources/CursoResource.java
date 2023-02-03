package com.magadiflo.dk.ms.cursos.app.resources;

import com.magadiflo.dk.ms.cursos.app.entity.Curso;
import com.magadiflo.dk.ms.cursos.app.services.ICursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/cursos")
public class CursoResource {

    private final ICursoService cursoService;

    public CursoResource(ICursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public ResponseEntity<List<Curso>> listar() {
        return ResponseEntity.ok(this.cursoService.listar());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Curso> detalle(@PathVariable Long id) {
        return this.cursoService.porId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
