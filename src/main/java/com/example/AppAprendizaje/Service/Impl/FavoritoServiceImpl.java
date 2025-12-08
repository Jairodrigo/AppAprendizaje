package com.example.AppAprendizaje.Service.Impl;

import com.example.AppAprendizaje.Model.Favorito;
import com.example.AppAprendizaje.Model.Proyecto;
import com.example.AppAprendizaje.Model.Usuario;
import com.example.AppAprendizaje.Repository.FavoritoRepository;
import com.example.AppAprendizaje.Service.FavoritoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoritoServiceImpl implements FavoritoService {
    private final FavoritoRepository favoritoRepository;

    public FavoritoServiceImpl(FavoritoRepository favoritoRepository) {
        this.favoritoRepository = favoritoRepository;
    }

    @Override
    public Favorito marcarFavorito(Usuario usuario, Proyecto proyecto) {
        if (favoritoRepository.existsByUsuarioAndProyecto(usuario, proyecto)) {
            return null; // ya existe
        }

        Favorito favorito = new Favorito();
        favorito.setUsuario(usuario);
        favorito.setProyecto(proyecto);
        return favoritoRepository.save(favorito);
    }

    @Override
    @Transactional
    public void quitarFavorito(Usuario usuario, Proyecto proyecto) {

        Optional<Favorito> favoritoOpt =
                favoritoRepository.findByUsuarioAndProyecto(usuario, proyecto);

        if (favoritoOpt.isPresent()) {
            favoritoRepository.delete(favoritoOpt.get());
        }
    }


    @Override
    public boolean esFavorito(Usuario usuario, Proyecto proyecto) {
        return favoritoRepository.existsByUsuarioAndProyecto(usuario, proyecto);
    }

    @Override
    public List<Favorito> obtenerFavoritosPorUsuario(Usuario usuario) {
        return favoritoRepository.findByUsuario(usuario);
    }
}
