package com.example.AppAprendizaje.Repository;

import com.example.AppAprendizaje.Model.Favorito;
import com.example.AppAprendizaje.Model.Proyecto;
import com.example.AppAprendizaje.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritoRepository extends JpaRepository<Favorito, Integer> {

    Optional<Favorito> findByUsuarioAndProyecto(Usuario usuario, Proyecto proyecto);

    List<Favorito> findByUsuario(Usuario usuario);

    boolean existsByUsuarioAndProyecto(Usuario usuario, Proyecto proyecto);

    void deleteByUsuarioAndProyecto(Usuario usuario, Proyecto proyecto);

    List<Favorito> findByUsuarioIdUsuario(Short idUsuario);
    Optional<Favorito> findByUsuarioIdUsuarioAndProyectoIdProyecto(Short idUsuario, Integer idProyecto);
}
