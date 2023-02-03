package com.magadiflo.dk.ms.cursos.app.resources;

import com.magadiflo.dk.ms.cursos.app.entity.Curso;
import com.magadiflo.dk.ms.cursos.app.services.ICursoService;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    public ResponseEntity<Curso> guardar(@RequestBody Curso curso) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.cursoService.guardar(curso));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Curso> editar(@PathVariable Long id, @RequestBody Curso curso) {
        return this.cursoService.porId(id)
                .map(cursoBD -> {
                    cursoBD.setNombre(curso.getNombre());
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(this.cursoService.guardar(cursoBD));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return this.cursoService.porId(id)
                .map(curso -> {
                    this.cursoService.eliminar(curso.getId());
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
