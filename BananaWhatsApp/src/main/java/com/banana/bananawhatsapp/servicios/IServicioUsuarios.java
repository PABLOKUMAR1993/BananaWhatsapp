package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
public interface IServicioUsuarios {

    public Usuario obtener(int id) throws UsuarioException;
    public Usuario crearUsuario(Usuario usuario) throws UsuarioException;
    public boolean borrarUsuario(Usuario usuario) throws UsuarioException;
    public Usuario actualizarUsuario(Usuario usuario) throws UsuarioException;
    public Set<Usuario> obtenerPosiblesDestinatarios(Usuario usuario, int max) throws UsuarioException;

}
