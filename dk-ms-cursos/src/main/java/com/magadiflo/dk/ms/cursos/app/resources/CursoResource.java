package com.magadiflo.dk.ms.cursos.app.resources;

import com.magadiflo.dk.ms.cursos.app.entity.Curso;
import com.magadiflo.dk.ms.cursos.app.services.ICursoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> guardar(@Valid @RequestBody Curso curso, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(this.mensajeErrores(result));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.cursoService.guardar(curso));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @Valid @RequestBody Curso curso, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(this.mensajeErrores(result));
        }
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

    private Map<String, String> mensajeErrores(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(fieldError -> {
            errores.put(fieldError.getField(), String.format("El campo %s %s", fieldError.getField(), fieldError.getDefaultMessage()));
        });
        return errores;
    }

}
