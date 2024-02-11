package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.servicios.IServicioUsuarios;
import com.banana.bananawhatsapp.util.DBUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class UsuarioRepositoryTest {

    //////

    @Autowired
    IServicioUsuarios servicioUsuarios;

    //////

    @BeforeEach
    void cleanAndReloadData() {
        DBUtil.reloadDB();
    }

    @Test
    @Order(1)
    void dadoUnUsuarioValido_cuandoCrear_entoncesUsuarioValido() throws Exception {
        Usuario nuevo = new Usuario(null, "Ricardo", "r@r.com", LocalDate.now(), true, new ArrayList<>());
        servicioUsuarios.crearUsuario(nuevo);

        assertThat(nuevo, notNullValue());
        assertThat(nuevo.getId(), greaterThan(0));
    }

    @Test
    @Order(2)
    void dadoUnUsuarioNOValido_cuandoCrear_entoncesExcepcion() {
        Usuario nuevo = new Usuario(null, "", "r", LocalDate.now(), true, new ArrayList<>());
        assertThrows(Exception.class, () -> {
            servicioUsuarios.crearUsuario(nuevo);
        });
    }

    @Test
    @Order(3)
    void dadoUnUsuarioValido_cuandoActualizar_entoncesUsuarioValido() throws Exception {
        Integer iDUser = 1;
        Usuario user = new Usuario(iDUser, "Juan", "j@j.com", LocalDate.now(), true, new ArrayList<>());
        Usuario userUpdate = servicioUsuarios.actualizarUsuario(user);
        assertThat(userUpdate.getNombre(), is("Juan"));
    }

    @Test
    @Order(4)
    void dadoUnUsuarioNOValido_cuandoActualizar_entoncesExcepcion() throws Exception {
        Integer iDUser = -1;
        Usuario user = new Usuario(iDUser, "Juan", "j@j.com", LocalDate.now(), true, new ArrayList<>());
        assertThrows(UsuarioException.class, () -> {
            servicioUsuarios.actualizarUsuario(user);
        });
    }

    @Test
    @Order(5)
    void dadoUnUsuarioValido_cuandoBorrar_entoncesOK() throws SQLException {
        Usuario user = new Usuario(1, null, null, null, true, new ArrayList<>());
        boolean ok = servicioUsuarios.borrarUsuario(user);
        assertTrue(ok);
    }

    @Test
    @Order(6)
    void dadoUnUsuarioNOValido_cuandoBorrar_entoncesExcepcion() throws Exception {
        Usuario user = new Usuario(-1, null, null, null, true, new ArrayList<>());
        assertThrows(Exception.class, () -> {
            servicioUsuarios.borrarUsuario(user);
        });
    }

    @Test
    @Order(7)
    void dadoUnUsuarioValido_cuandoObtenerPosiblesDestinatarios_entoncesLista() throws Exception {
        Integer iDUser = 1;
        int numPosibles = 100;
        Usuario user = new Usuario(iDUser, "Juan", "j@j.com", LocalDate.now(), true, new ArrayList<>());

        Set<Usuario> conjuntoDestinatarios = servicioUsuarios.obtenerPosiblesDestinatarios(user, numPosibles);
        assertTrue(conjuntoDestinatarios.size() <= numPosibles);
    }

    @Test
    @Order(8)
    void dadoUnUsuarioNOValido_cuandoObtenerPosiblesDestinatarios_entoncesExcepcion() throws Exception {
        Usuario user = new Usuario(-1, null, null, null, true, new ArrayList<>());
        int numPosibles = 100;
        assertThrows(UsuarioException.class, () -> {
            Set<Usuario> conjuntoDestinatarios = servicioUsuarios.obtenerPosiblesDestinatarios(user, numPosibles);
        });

    }


}