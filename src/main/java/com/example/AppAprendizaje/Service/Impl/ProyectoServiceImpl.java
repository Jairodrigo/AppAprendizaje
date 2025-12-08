package com.example.AppAprendizaje.Service.Impl;

import com.example.AppAprendizaje.Dto.*;
import com.example.AppAprendizaje.Model.Favorito;
import com.example.AppAprendizaje.Model.Interaccion;
import com.example.AppAprendizaje.Model.Proyecto;
import com.example.AppAprendizaje.Model.Recurso;
import com.example.AppAprendizaje.Repository.FavoritoRepository;
import com.example.AppAprendizaje.Repository.InteraccionRepository;
import com.example.AppAprendizaje.Repository.ProyectoRepository;
import com.example.AppAprendizaje.Repository.UsuarioRepository;
import com.example.AppAprendizaje.Service.ProyectoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

//Implementamos la logica del servicio
@Service
public class ProyectoServiceImpl implements ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final UsuarioRepository usuarioRepository;
    private final InteraccionRepository interaccionRepository; // <- agregar
    private final FavoritoRepository favoritoRepository;

    @Autowired
    public ProyectoServiceImpl(ProyectoRepository proyectoRepository,
                               UsuarioRepository usuarioRepository,
                               InteraccionRepository interaccionRepository,
                               FavoritoRepository favoritoRepository) { // <- agregar
        this.proyectoRepository = proyectoRepository;
        this.favoritoRepository = favoritoRepository;
        this.usuarioRepository = usuarioRepository;
        this.interaccionRepository = interaccionRepository; // <- asignar
    }

    @Override
    public Optional<Proyecto> obtenerProyectoPorId(Integer id) {
        return proyectoRepository.findById(id);
    }

    @Override
    public List<Proyecto> listarProyectosPorUsuario(Short usuarioId) {
        List<Proyecto> proyectos = proyectoRepository.findByUsuarioIdUsuario(usuarioId);
        List<Favorito> favoritos = favoritoRepository.findByUsuarioIdUsuario(usuarioId);

        Set<Integer> idsFavoritos = favoritos.stream()
                .map(f -> f.getProyecto().getIdProyecto())
                .collect(Collectors.toSet());

        proyectos.forEach(p ->
                p.setEsFavorito(idsFavoritos.contains(p.getIdProyecto()))
        );

        return proyectos;
    }

    @Override
    public List<ProyectoPublicoDto> obtenerProyectosPublicos() {

        List<Proyecto> proyectos = proyectoRepository.findByTipo(Proyecto.TipoProyecto.PB);

        List<ProyectoPublicoDto> resultado = new ArrayList<>();

        for (Proyecto p : proyectos) {

            ProyectoPublicoDto dto = new ProyectoPublicoDto();
            dto.setIdProyecto(p.getIdProyecto());
            dto.setNombreProyecto(p.getNombreProyecto());
            dto.setDescripcion(p.getDescripcion());
            dto.setImagenPortada(p.getImagenPortada());

            // SACAR NOMBRE DEL USUARIO
            dto.setNombreUsuario(p.getUsuario().getNombre());
            // O .getNombreCompleto(), según tu entidad

            // PROMEDIO DE VALORACIÓN
            dto.setPromedioValoracion(p.getPromedioValoracion() != null ?
                    p.getPromedioValoracion() : 0f);

            resultado.add(dto);
        }

        return resultado;
    }


    @Override
    public ProyectoDto getProyectoCompleto(Integer idProyecto) {
        Proyecto proyecto = proyectoRepository.findById(idProyecto)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        // Mapear escenas
        List<EscenaDto> escenasDto = proyecto.getEscenas().stream().map(escena -> {
            EscenaDto escenaDto = new EscenaDto();
            escenaDto.setIdEscena(escena.getIdEscena());
            escenaDto.setNombre(escena.getNombre());
            escenaDto.setDescripcion(escena.getDescripcion());
            escenaDto.setColorFondo(escena.getColorFondo());
            escenaDto.setFecha(escena.getFecha());
            escenaDto.setOrden(escena.getOrden());
            escenaDto.setIdPlantilla(escena.getPlantilla() != null ? escena.getPlantilla().getIdPlantilla() : null);
            escenaDto.setIdProyecto(proyecto.getIdProyecto());

            // Mapear recursos
            List<UsoRecursoDto> recursosDto = escena.getUsosRecursos().stream() // solo imágenes
                    .map(uso -> {
                        UsoRecursoDto usoDto = new UsoRecursoDto();
                        usoDto.setIdUsoRecurso(uso.getIdUsoRecurso());
                        usoDto.setAlto(uso.getAlto());
                        usoDto.setAncho(uso.getAncho());
                        usoDto.setX(uso.getX());
                        usoDto.setY(uso.getY());
                        usoDto.setIdEscena(escena.getIdEscena());
                        usoDto.setIdRecurso(uso.getRecurso().getIdRecurso());
                        usoDto.setRutaImagen("http://localhost:8080/uploads/" + uso.getRecurso().getUrlArchivo());
                        usoDto.setNombreRecurso(uso.getRecurso().getNombreRecurso());
                        usoDto.setForma(uso.getForma().name());

                        // Buscar reacción asociada a esta imagen
                        interaccionRepository.findByUsoRecursoIdUsoRecurso(uso.getIdUsoRecurso())
                                .stream()
                                .findFirst() // tomamos la primera reacción, si existe
                                .ifPresent(interaccion -> {
                                    Recurso recursoReaccion = interaccion.getRecurso();
                                    if (!recursoReaccion.getIdRecurso().equals(uso.getRecurso().getIdRecurso())) {
                                        UsoRecursoDto.Reaccion reaccionDto = new UsoRecursoDto.Reaccion();
                                        reaccionDto.setNombreRecurso(recursoReaccion.getNombreRecurso());
                                        reaccionDto.setTipoRecurso(recursoReaccion.getTipoRecurso());
                                        reaccionDto.setUrl("http://localhost:8080/uploads/" + recursoReaccion.getUrlArchivo());
                                        usoDto.setReaccion(reaccionDto);
                                    }
                                });

                        if (uso.getRecurso() != null) {
                            usoDto.setIdRecurso(uso.getRecurso().getIdRecurso());
                            usoDto.setRutaImagen("http://localhost:8080/uploads/" + uso.getRecurso().getUrlArchivo());
                            usoDto.setNombreRecurso(uso.getRecurso().getNombreRecurso());
                        }

                        return usoDto;
                    }).toList();


            escenaDto.setUsosRecursos(recursosDto);
            return escenaDto;
        }).toList();

        // Crear DTO final del proyecto
        ProyectoDto proyectoDto = new ProyectoDto();
        proyectoDto.setIdProyecto(proyecto.getIdProyecto());
        proyectoDto.setNombreProyecto(proyecto.getNombreProyecto());
        proyectoDto.setDescripcion(proyecto.getDescripcion());
        proyectoDto.setEscenas(escenasDto);

        return proyectoDto;
    }

}
