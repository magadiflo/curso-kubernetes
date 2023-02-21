package com.magadiflo.dk.ms.cursos.app.clients;

import com.magadiflo.dk.ms.cursos.app.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * De forma automática este cliente @FeignClient es un componente
 * de Spring, es decir, que ya puede ser inyectado en las clases
 * de componente.
 */

@FeignClient(name = "dk-ms-usuarios", url = "http://localhost:8001", path = "/api/v1/usuarios")
public interface IUsuarioClientFeign {
    @GetMapping(path = "/{id}")
    Usuario detalle(@PathVariable Long id);

    /**
     * IMPORTANTE
     * **********
     * En el método guardar del microservicio usuarios, vemos que tiene
     * otras anotaciones como:
     * ...guardar(@Valid @RequestBody Usuario usuario, BindingResult result)
     * Tanto las anotaciones @Valid, como el objeto BindingResult
     * se ejecutarán en dicho microservicio para validar el objeto que le llega.
     * En nuestro caso, aquí solo le enviaremos el @RequestBody Usuario usuario,
     * porque finalmente es lo que está esperando que le enviemos dicho
     * microservicio.
     */
    @PostMapping
    Usuario guardar(@RequestBody Usuario usuario);


    /**
     * Usamos Iterable en reemplazo de List, ya que usando Feign con List
     * aparentemente trae problemas.
     */
    @GetMapping(path = "/usuarios-por-curso")
    List<Usuario> obtenerAlumnosPorCurso(@RequestParam Iterable<Long> usuarioIds);
}
