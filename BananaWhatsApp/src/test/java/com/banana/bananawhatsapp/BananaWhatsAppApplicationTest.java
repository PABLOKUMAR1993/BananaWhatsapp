package com.banana.bananawhatsapp;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.persistencia.IMensajeRepository;
import com.banana.bananawhatsapp.servicios.IServicioMensajeria;
import com.banana.bananawhatsapp.util.DBUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class BananaWhatsAppApplicationTest {

    @Autowired
    private IMensajeRepository mensajeRepository;

    @Autowired
    private IServicioMensajeria servicioMensajeria;

    @Test
    public void servicioShouldNotBeNull(){
        assertNotNull(servicioMensajeria);
    }

    @Test
    public void repositorioShouldNotBeNull(){
        assertNotNull(mensajeRepository);
    }
    @Test
    public void load() {
        DBUtil.reloadDB();
        assertTrue(true);
    }
}