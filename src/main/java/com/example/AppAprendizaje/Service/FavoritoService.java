package com.example.AppAprendizaje.Service;

import com.example.AppAprendizaje.Model.Favorito;
import com.example.AppAprendizaje.Model.Proyecto;
import com.example.AppAprendizaje.Model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FavoritoService {
    Favorito marcarFavorito(Usuario usuario, Proyecto proyecto);
    void quitarFavorito(Usuario usuario, Proyecto proyecto);
    boolean esFavorito(Usuario usuario, Proyecto proyecto);
    List<Favorito> obtenerFavoritosPorUsuario(Usuario usuario);
}
