package com.magadiflo.dk.ms.usuarios.app.resources;

import com.magadiflo.dk.ms.usuarios.app.models.entity.Usuario;
import com.magadiflo.dk.ms.usuarios.app.services.IUsuarioService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final ApplicationContext context;

    private final Environment env;

    private final PasswordEncoder passwordEncoder;

    public UsuarioResource(IUsuarioService usuarioService, ApplicationContext context, Environment env, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.context = context;
        this.env = env;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(path = "/crash")
    public void crash() {
        // Simulando un error o quiebre de la aplicación
        ((ConfigurableApplicationContext) this.context).close();
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listarTodos() {
        Map<String, Object> body = new HashMap<>();
        body.put("users", this.usuarioService.listar());
        body.put("podinfo", String.format("%s: %s", this.env.getProperty("MY_POD_NAME"), this.env.getProperty("MY_POD_IP")));
        body.put("texto", this.env.getProperty("config.texto"));
        return ResponseEntity.ok(body);
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

        usuario.setPassword(this.passwordEncoder.encode(usuario.getPassword()));

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
                    usuarioBD.setPassword(this.passwordEncoder.encode(usuario.getPassword()));
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

    @GetMapping(path = "/authorized")
    public Map<String, Object> authorized(@RequestParam String code) {
        return Collections.singletonMap("code", code);
    }


    private Map<String, String> mensajeErrores(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(fieldError -> {
            errores.put(fieldError.getField(), String.format("Ocurrió un error, el campo %s %s", fieldError.getField(), fieldError.getDefaultMessage()));
        });
        return errores;
    }
}
