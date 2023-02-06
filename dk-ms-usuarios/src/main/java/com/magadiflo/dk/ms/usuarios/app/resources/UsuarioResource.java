package com.magadiflo.dk.ms.usuarios.app.resources;

import com.magadiflo.dk.ms.usuarios.app.models.entity.Usuario;
import com.magadiflo.dk.ms.usuarios.app.services.IUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/usuarios")
public class UsuarioResource {

    private final IUsuarioService usuarioService;

    public UsuarioResource(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(this.usuarioService.listar());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Usuario> detalle(@PathVariable Long id) {
        return this.usuarioService.porId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(this.mensajeErrores(result));
        }

        if (this.usuarioService.existePorEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("email", String.format("Ya existe un usuario con el email %s", usuario.getEmail())));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.usuarioService.guardar(usuario));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @Valid @RequestBody Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(this.mensajeErrores(result));
        }
        return this.usuarioService.porId(id)
                .map(usuarioBD -> {

                    if (!usuario.getEmail().equalsIgnoreCase(usuarioBD.getEmail()) &&
                            this.usuarioService.porEmail(usuario.getEmail()).isPresent()) {
                        return ResponseEntity.badRequest()
                                .body(Collections.singletonMap("email", String.format("Ya existe un usuario con el email %s", usuario.getEmail())));
                    }

                    usuarioBD.setNombre(usuario.getNombre());
                    usuarioBD.setEmail(usuario.getEmail());
                    usuarioBD.setPassword(usuario.getPassword());
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(this.usuarioService.guardar(usuarioBD));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return this.usuarioService.porId(id)
                .map(usuario -> {
                    this.usuarioService.eliminar(usuario.getId());
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/usuarios-por-curso")
    public ResponseEntity<List<Usuario>> obtenerAlumnosPorCurso(@RequestParam List<Long> usuarioIds) {
        return ResponseEntity.ok(this.usuarioService.listarPorIds(usuarioIds));
    }

    private Map<String, String> mensajeErrores(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(fieldError -> {
            errores.put(fieldError.getField(), String.format("El campo %s %s", fieldError.getField(), fieldError.getDefaultMessage()));
        });
        return errores;
    }
}
