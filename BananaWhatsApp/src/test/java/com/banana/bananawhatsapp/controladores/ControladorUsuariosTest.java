package com.banana.bananawhatsapp.controladores;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.servicios.IServicioUsuarios;
import com.banana.bananawhatsapp.util.DBUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ControladorUsuariosTest {

    //////

    @Autowired
    ControladorUsuarios controladorUsuarios;

    @Autowired
    IServicioUsuarios servicioUsuarios;

    //////

    @BeforeEach
    void cleanAndReloadData() {
        DBUtil.reloadDB();
    }

    @Test
    void dadoUsuarioValido_cuandoAlta_entoncesUsuarioValido() {
        Usuario nuevo = new Usuario(null, "Ricardo", "r@r.com", LocalDate.now(), true, null);
        controladorUsuarios.alta(nuevo);

        assertThat(nuevo, notNullValue());
        assertThat(nuevo.getId(), greaterThan(0));
    }

    @Test
    void dadoUsuarioNOValido_cuandoAlta_entoncesExcepcion() {
        Usuario user = new Usuario(null, "", "g@gccom", LocalDate.now(), true, null);
        assertThrows(Exception.class, () -> {
            controladorUsuarios.alta(user);
        });
    }

    @Test
    @Transactional
    void dadoUsuarioValido_cuandoActualizar_entoncesUsuarioValido() throws Exception {
        Integer iDUser = 2;
        LocalDate fecha = LocalDate.parse("2023-12-17");
        Usuario user = servicioUsuarios.obtener(iDUser);
        user.setNombre("Juan Luis");
        user.setEmail("jl@jll.com");
        user.setAlta(fecha);
        controladorUsuarios.actualizar(user);
        assertThat(servicioUsuarios.obtener(iDUser).getNombre(), is("Juan Luis"));
    }

    @Test
    @Transactional
    void dadoUsuarioNOValido_cuandoActualizar_entoncesExcepcion() {
        assertThrows(Exception.class, () -> {
            Integer iDUser = 3;
            LocalDate fecha = LocalDate.parse("2025-12-17");
            Usuario user = servicioUsuarios.obtener(iDUser);
            user.setNombre("Juan Luis");
            user.setEmail("jl@jll.com");
            user.setAlta(fecha);
            controladorUsuarios.actualizar(user);
        });
    }

    @Test
    void dadoUsuarioValido_cuandoBaja_entoncesUsuarioValido() throws Exception {
        Usuario user = servicioUsuarios.obtener(1);
        boolean ok = controladorUsuarios.baja(user);
        assertThat(ok, is(true));
    }

    @Test
    void dadoUsuarioNOValido_cuandoBaja_entoncesExcepcion() {
        Usuario user = new Usuario(-1, null, null, null, true, null);
        assertThrows(Exception.class, () -> {
            controladorUsuarios.baja(user);
        });
    }

    //////

}