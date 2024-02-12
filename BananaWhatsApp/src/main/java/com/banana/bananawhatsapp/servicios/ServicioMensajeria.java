package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IMensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ServicioMensajeria implements IServicioMensajeria {
    @Autowired
    private IMensajeRepository mensajeRepository;


    @Override
    public Mensaje enviarMensaje(Usuario remitente, Usuario destinatario, String texto) throws UsuarioException, MensajeException {
        try {
            if (remitente != null && destinatario != null && texto.length() > 10) {
                Mensaje mensaje = new Mensaje();
                mensaje.setRemitente(remitente);
                mensaje.setCuerpo(texto);
                mensaje.setFecha(LocalDate.now());
                return crear(mensaje);
            } else {
                throw new MensajeException("Error al enviar el mensaje");
            }
        } catch (Exception e) {
            throw new MensajeException("Error al enviar el mensaje" + e.getMessage());
        }


    }

    @Override
    public List<Mensaje> mostrarChatConUsuario(Usuario remitente, Usuario destinatario) throws UsuarioException, MensajeException {

        try {
            if (!mensajeRepository.existsById(remitente.getId()) || !mensajeRepository.existsById(destinatario.getId()))
                throw new MensajeException("el remitente o destinatario n oexisten");
            else {
                return mensajeRepository.findAllByRemitenteIdAndDestinatarioId(remitente, destinatario);
            }

        } catch (Exception e) {
            throw new MensajeException("Error al buscar los mensajes" + e.getMessage());
        }
    }

    @Override
    public boolean borrarChatConUsuario(Usuario remitente, Usuario destinatario) throws UsuarioException, MensajeException {
        try {
            if (!mensajeRepository.existsById(remitente.getId()) || !mensajeRepository.existsById(destinatario.getId()))
                throw new MensajeException("el remitente o destinatario n oexisten");
            return borrarEntre(remitente, destinatario);

        } catch (Exception e) {
            throw new MensajeException("Error al eliminar el mensaje" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Mensaje crear(Mensaje mensaje) throws Exception {
        try {
            if (mensaje.getCuerpo().isEmpty()) {
                throw new MensajeException(("El cuerpo del mensaje no debe estar vacio"));
            } else {
                return mensajeRepository.save(mensaje);
            }
        } catch (Exception e) {
            throw new MensajeException("Error al crear mensaje" + e.getMessage());
        }
    }

    @Override
    public List<Mensaje> obtener(Usuario usuario) throws SQLException {
        try {
            if (usuario.getId() != null && usuario.getId() > 0) {
                return mensajeRepository.findAllById(Collections.singletonList(usuario.getId()));
            } else {
                throw new UsuarioException("El usuario no existe o es nulo");
            }
        } catch (Exception e) {
            throw new UsuarioException(("Error al obtener el mensaje" + e.getMessage()));
        }

    }

    @Override
    @Transactional
    public boolean borrarEntre(Usuario remitente, Usuario destinatario) throws Exception {
        List<Mensaje> mensajesEntreUsuarios = mensajeRepository.findAllByRemitenteIdAndDestinatarioId(remitente, destinatario);
        boolean hayMensajes = !mensajesEntreUsuarios.isEmpty();

        for (Mensaje mensaje : mensajesEntreUsuarios) {
            mensajeRepository.delete(mensaje);
        }
        return hayMensajes;
    }

    @Override
    public boolean borrarTodos(Usuario usuario) throws SQLException {
        if (!mensajeRepository.existsById(usuario.getId())) {
            throw new UsuarioException("el usuario es incorrecto");
        } else {
            List<Mensaje> mensajes = obtener(usuario);
            boolean hayMensajes = !mensajes.isEmpty();
            for (Mensaje mensaje : mensajes) mensajeRepository.delete(mensaje);
            return hayMensajes;

        }


    }

}
