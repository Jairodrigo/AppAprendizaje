package com.example.AppAprendizaje.Service;

import com.example.AppAprendizaje.Model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario crearUsuario(Usuario usuario);
    Optional<Usuario> obtenerUsuarioPorId(Short id);

    //para login con Google
    Optional<Usuario> obtenerUsuarioPorCorreo(String correo);
}
