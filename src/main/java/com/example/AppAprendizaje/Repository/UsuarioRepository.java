package com.example.AppAprendizaje.Repository;

import com.example.AppAprendizaje.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Short> {
    //buscamos al usuario por correo
    Optional<Usuario> findByCorreo(String correo);
}
