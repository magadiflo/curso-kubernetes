package com.magadiflo.dk.ms.cursos.app.models;

/**
 * Esta clase de models, no es un Entity, sino una clase de modelo
 * que representa la estructura de la entidad a la que va
 * a hacer referencia en el microservicio usuarios. Ya que, de alguna
 * manera, por ejemplo, cuando se solicite todos los Usuarios
 * que están registrados en un curso, esta clase nos va a servir para
 * representar dicha información en el objeto JSON.
 */

public class Usuario {
    private Long id;
    private String nombre;
    private String email;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Usuario{");
        sb.append("id=").append(id);
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
