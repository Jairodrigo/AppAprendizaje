package com.example.AppAprendizaje.Controller;

import com.example.AppAprendizaje.Model.Favorito;
import com.example.AppAprendizaje.Model.Proyecto;
import com.example.AppAprendizaje.Model.Usuario;
import com.example.AppAprendizaje.Repository.FavoritoRepository;
import com.example.AppAprendizaje.Repository.ProyectoRepository;
import com.example.AppAprendizaje.Repository.UsuarioRepository;
import com.example.AppAprendizaje.Service.FavoritoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/favoritos")
@CrossOrigin(origins = "http://localhost:4200")
public class FavoritoController {

    private final FavoritoService favoritoService;
    private final UsuarioRepository usuarioRepository;
    private final ProyectoRepository proyectoRepository;
    private final FavoritoRepository favoritoRepository;

    public FavoritoController(FavoritoService favoritoService,FavoritoRepository favoritoRepository, UsuarioRepository usuarioRepository, ProyectoRepository proyectoRepository) {
        this.favoritoService = favoritoService;
        this.usuarioRepository = usuarioRepository;
        this.proyectoRepository = proyectoRepository;
        this.favoritoRepository = favoritoRepository;
    }

    @PostMapping("/marcar")
    public Map<String, Object> marcarFavorito(
            @RequestParam Short idUsuario,
            @RequestParam Integer idProyecto) {

        Map<String, Object> response = new HashMap<>();

        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        Proyecto proyecto = proyectoRepository.findById(idProyecto).orElse(null);

        if (usuario == null || proyecto == null) {
            response.put("success", false);
            response.put("message", "Usuario o proyecto no encontrado");
            return response;
        }

        Favorito fav = favoritoService.marcarFavorito(usuario, proyecto);
        response.put("success", fav != null);
        return response;
    }


    @DeleteMapping("/quitar")
    public Map<String, Object> quitarFavorito(
            @RequestParam Short idUsuario,
            @RequestParam Integer idProyecto) {

        Map<String, Object> response = new HashMap<>();

        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        Proyecto proyecto = proyectoRepository.findById(idProyecto).orElse(null);

        if (usuario == null || proyecto == null) {
            response.put("success", false);
            response.put("message", "Usuario o proyecto no encontrado");
            return response;
        }

        favoritoService.quitarFavorito(usuario, proyecto);

        response.put("success", true);
        return response;
    }


    @GetMapping("/existe")
    public Map<String, Boolean> existeFavorito(@RequestParam Short idUsuario, @RequestParam Integer idProyecto) {

        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        Proyecto proyecto = proyectoRepository.findById(idProyecto).orElse(null);

        boolean esFav = favoritoService.esFavorito(usuario, proyecto);

        Map<String, Boolean> response = new HashMap<>();
        response.put("favorito", esFav);

        return response;
    }

    @PostMapping("/{idProyecto}/{idUsuario}")
    public ResponseEntity<?> agregarFavorito(
            @PathVariable Integer idProyecto,
            @PathVariable Short idUsuario) {

        if (favoritoRepository.findByUsuarioIdUsuarioAndProyectoIdProyecto(idUsuario, idProyecto).isPresent()) {
            return ResponseEntity.ok("Ya existe");
        }

        Favorito fav = new Favorito();
        fav.setUsuario(usuarioRepository.findById(idUsuario).get());
        fav.setProyecto(proyectoRepository.findById(idProyecto).get());

        favoritoRepository.save(fav);

        return ResponseEntity.ok("Agregado");
    }

    // ELIMINAR FAVORITO
    @DeleteMapping("/{idProyecto}/{idUsuario}")
    public ResponseEntity<?> eliminarFavorito(
            @PathVariable Integer idProyecto,
            @PathVariable Short idUsuario) {

        Optional<Favorito> fav =
                favoritoRepository.findByUsuarioIdUsuarioAndProyectoIdProyecto(idUsuario, idProyecto);

        if (fav.isPresent()) {
            favoritoRepository.delete(fav.get());
            return ResponseEntity.ok("Eliminado");
        }

        return ResponseEntity.ok("No existía");
    }
}
