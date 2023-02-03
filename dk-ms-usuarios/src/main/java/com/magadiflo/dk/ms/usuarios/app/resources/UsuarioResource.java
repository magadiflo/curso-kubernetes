package com.magadiflo.dk.ms.usuarios.app.resources;

import com.magadiflo.dk.ms.usuarios.app.models.entity.Usuario;
import com.magadiflo.dk.ms.usuarios.app.services.IUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Usuario> guardar(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.usuarioService.guardar(usuario));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Usuario> editar(@PathVariable Long id, @RequestBody Usuario usuario) {
        return this.usuarioService.porId(id)
                .map(usuarioBD -> {
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
}
