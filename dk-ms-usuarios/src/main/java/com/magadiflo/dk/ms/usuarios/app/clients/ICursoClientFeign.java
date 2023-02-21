package com.magadiflo.dk.ms.usuarios.app.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "dk-ms-cursos", url = "http://localhost:8002", path = "/api/v1/cursos")
public interface ICursoClientFeign {
    @DeleteMapping(path = "/eliminar-usuario/{usuarioId}")
    void eliminarCursoUsuarioPorId(@PathVariable Long usuarioId);
}
