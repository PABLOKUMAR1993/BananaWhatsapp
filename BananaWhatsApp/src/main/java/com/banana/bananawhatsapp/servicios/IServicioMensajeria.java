package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
@Service
public interface IServicioMensajeria {

    public Mensaje enviarMensaje(Usuario remitente, Usuario destinatario, String texto) throws UsuarioException, MensajeException;

    public List<Mensaje> mostrarChatConUsuario(Usuario remitente, Usuario destinatario) throws UsuarioException, MensajeException;

    public boolean borrarChatConUsuario(Usuario remitente, Usuario destinatario) throws UsuarioException, MensajeException;

    public Mensaje crear(Mensaje mensaje) throws Exception;

    public List<Mensaje> obtener(Usuario usuario) throws SQLException;

    public boolean borrarEntre(Usuario remitente, Usuario destinatario) throws Exception;

    public boolean borrarTodos(Usuario usuario) throws SQLException;


}
