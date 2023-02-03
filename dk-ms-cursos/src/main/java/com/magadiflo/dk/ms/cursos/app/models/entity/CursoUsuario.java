package com.magadiflo.dk.ms.cursos.app.models.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "curso_usuarios")
public class CursoUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "usuario_id", unique = true)
    private Long usuarioId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    // Implementamos este método equals(), para que cuando en la
    // clase Curso, su método removeCursoUsuario(...) elimine el CursoUsuario correctamente,
    // ya que al eliminar hará la comparación hasta encontrar el objeto a eliminar; por defecto
    // la comparación lo hace por instancia (por referencia), por eso modificamos el método
    // equals para que la comparación lo haga por el id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CursoUsuario that = (CursoUsuario) o;
        return Objects.equals(id, that.id) && Objects.equals(usuarioId, that.usuarioId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CursoUsuario{");
        sb.append("id=").append(id);
        sb.append(", usuarioId=").append(usuarioId);
        sb.append('}');
        return sb.toString();
    }
}
