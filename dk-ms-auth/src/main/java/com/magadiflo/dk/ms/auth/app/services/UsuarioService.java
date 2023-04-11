package com.magadiflo.dk.ms.auth.app.services;

import com.magadiflo.dk.ms.auth.app.models.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Service
public class UsuarioService implements UserDetailsService {

    private final WebClient.Builder client;

    private static final Logger LOG = LoggerFactory.getLogger(UsuarioService.class);

    public UsuarioService(WebClient.Builder client) {
        this.client = client;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Usuario usuario = this.client.build()
                    .get()
                    .uri("http://dk-ms-usuarios/login", uriBuilder -> uriBuilder.queryParam("email", email).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(Usuario.class)
                    .block();

            LOG.info("Usuario login: {}", usuario.getEmail());
            LOG.info("Usuario nombre: {}", usuario.getNombre());
            LOG.info("Usuario password: {}", usuario.getPassword());

            return new User(email, usuario.getPassword(),
                    true, true, true, true,
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        } catch (RuntimeException e) {
            String error = String.format("Error en el login, no existe el usuario en el sistema %s en el sistema", email);
            LOG.error(error);
            LOG.error(e.getMessage());
            throw new UsernameNotFoundException(error);
        }
    }

}
