package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IMensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ServicioMensajeria implements IServicioMensajeria {
    @Autowired
    private IMensajeRepository mensajeRepository;



    @Override
    public Mensaje enviarMensaje(Usuario remitente, Usuario destinatario, String texto) throws UsuarioException, MensajeException {
        return null;
    }

    @Override
    public List<Mensaje> mostrarChatConUsuario(Usuario remitente, Usuario destinatario) throws UsuarioException, MensajeException {

        try{
            if (!mensajeRepository.existsById(remitente.getId()) || !mensajeRepository.existsById(destinatario.getId())) throw new MensajeException("el remitente o destinatario n oexisten");
            return mensajeRepository.findAllByRemitenteIdAndDestinatarioId(remitente,destinatario);
        }catch (Exception e){
            throw new MensajeException("Error al buscar los mensajes"+e.getMessage());
        }
    }

    @Override
    public boolean borrarChatConUsuario(Usuario remitente, Usuario destinatario) throws UsuarioException, MensajeException {
        return false;
    }

    @Override
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
        try{
            if (usuario.getId() !=null && usuario.getId()>0){
               return  mensajeRepository.findAllById(Collections.singletonList(usuario.getId()));
            }
            else{
                throw new UsuarioException("El usuario no existe o es nulo");
            }
        }catch (Exception e){
           throw new UsuarioException(("Error al obtener el mensaje"+e.getMessage()));
        }

    }

    @Override
    public boolean borrarEntre(Usuario remitente, Usuario destinatario) throws Exception {
        return false;
    }

    @Override
    public boolean borrarTodos(Usuario usuario) throws SQLException {
        return false;
    }


}