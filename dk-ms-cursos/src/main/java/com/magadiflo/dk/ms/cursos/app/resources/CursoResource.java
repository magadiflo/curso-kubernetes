package com.magadiflo.dk.ms.cursos.app.resources;

import com.magadiflo.dk.ms.cursos.app.models.Usuario;
import com.magadiflo.dk.ms.cursos.app.models.entity.Curso;
import com.magadiflo.dk.ms.cursos.app.services.ICursoService;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

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
    public ResponseEntity<Curso> detalle(@PathVariable Long id, @RequestHeader(value = "Authorization", required = true) String token) {
        return this.cursoService.porIdConUsuarios(id, token)
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

    @PutMapping(path = "/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@PathVariable Long cursoId, @RequestBody Usuario usuario) {
        Optional<Usuario> usuarioOptional;
        try {
            usuarioOptional = this.cursoService.asignarUsuario(usuario, cursoId);
        } catch (FeignException e) {
            String msj = "No existe el usuario por el id o error en la comunicación: %s";
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", String.format(msj, e.getMessage())));
        }
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@PathVariable Long cursoId, @RequestBody Usuario usuario) {
        Optional<Usuario> usuarioOptional;
        try {
            usuarioOptional = this.cursoService.crearUsuario(usuario, cursoId);
        } catch (FeignException e) {
            String msj = "No se pudo crear el usuario o error en la comunicación: %s";
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", String.format(msj, e.getMessage())));
        }
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "/des-asignar-usuario/{cursoId}")
    public ResponseEntity<?> desAsignarUsuario(@PathVariable Long cursoId, @RequestBody Usuario usuario) {
        Optional<Usuario> usuarioOptional;
        try {
            usuarioOptional = this.cursoService.desAsignarUsuario(usuario, cursoId);
        } catch (FeignException e) {
            String msj = "No se pudo crear el usuario o error en la comunicación: %s";
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", String.format(msj, e.getMessage())));
        }
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "/eliminar-usuario/{usuarioId}")
    public ResponseEntity<?> eliminarCursoUsuarioPorId(@PathVariable Long usuarioId) {
        this.cursoService.eliminarCursoUsuarioPorId(usuarioId);
        return ResponseEntity.noContent().build();
    }

    private Map<String, String> mensajeErrores(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(fieldError -> {
            errores.put(fieldError.getField(), String.format("El campo %s %s", fieldError.getField(), fieldError.getDefaultMessage()));
        });
        return errores;
    }

}
