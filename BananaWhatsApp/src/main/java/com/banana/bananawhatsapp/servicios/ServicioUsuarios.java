package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ServicioUsuarios implements IServicioUsuarios {

    //////

    @Autowired
    private IUsuarioRepository usuarioRepository;

    //////

    @Override
    public Usuario obtener(int id) throws UsuarioException {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) throws UsuarioException {
        try {
            if (usuario.getNombre() == null || usuario.getNombre().isEmpty()) {
                throw new UsuarioException("El nombre de usuario no puede estar vacío");
            } else {
                return usuarioRepository.save(usuario);
            }
        } catch (Exception e) {
            throw new UsuarioException("Error al guardar usuario: " + e.getMessage());
        }
    }

    @Override
    public boolean borrarUsuario(Usuario usuario) throws UsuarioException {
        try {
            usuarioRepository.deleteById(usuario.getId());
            return true;
        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioException("El usuario no existe: " + e.getMessage());
        } catch (Exception e) {
            throw new UsuarioException("No se puede borrar porque el usuario tiene mensajes: " + e.getMessage());
        }
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) throws UsuarioException {
        try {
            if (!usuarioRepository.existsById(usuario.getId())) throw new UsuarioException("El usuario no existe");
            return usuarioRepository.save(usuario);
        } catch (Exception e) {
            throw new UsuarioException("Error al actualizar el usuario: " + e.getMessage());
        }
    }

    @Override
    public Set<Usuario> obtenerPosiblesDestinatarios(Usuario usuario, int max) throws UsuarioException {

        if (usuario == null || usuario.getId() < 0 || usuario.getNombre() == null || usuario.getEmail() == null) {
            throw new UsuarioException("El usuario proporcionado no es válido, error al obtener la lista de destinatarios");
        }

        List<Usuario> todosUsuarios = usuarioRepository.findAll();
        Set<Usuario> posiblesDestinatarios = new HashSet<>();

        for (Usuario u : todosUsuarios) { // Elimino el usuario remitente.
            if (!u.equals(usuario)) {
                posiblesDestinatarios.add(u);
            }
        }

        if (posiblesDestinatarios.size() > max) { // Limito la cantidad de usuarios
            Set<Usuario> subconjunto = new HashSet<>();
            int i = 0;
            for (Usuario u : posiblesDestinatarios) {
                if (i >= max) break;
                subconjunto.add(u);
                i++;
            }
            posiblesDestinatarios = subconjunto;
        }

        return posiblesDestinatarios;

    }

    //////

}
