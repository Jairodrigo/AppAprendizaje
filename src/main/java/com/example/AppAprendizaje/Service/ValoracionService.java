package com.example.AppAprendizaje.Service;

import com.example.AppAprendizaje.Dto.TraerValoracionDto;
import com.example.AppAprendizaje.Model.Proyecto;
import com.example.AppAprendizaje.Model.Usuario;
import com.example.AppAprendizaje.Model.Valoracion;
import com.example.AppAprendizaje.Repository.ProyectoRepository;
import com.example.AppAprendizaje.Repository.UsuarioRepository;
import com.example.AppAprendizaje.Repository.ValoracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ValoracionService {

    @Autowired
    private ValoracionRepository valoracionRespository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    public Valoracion guardarValoracion(Short idUsuario, Integer idProyecto,
                                        Integer calificacion, String comentario) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Proyecto proyecto = proyectoRepository.findById(idProyecto)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        // Verificar si ya existe valoracion previa
        Optional<Valoracion> existente = valoracionRespository.findByUsuarioAndProyecto(usuario, proyecto);

        Valoracion v;
        if (existente.isPresent()) {
            // Actualiza la existente
            v = existente.get();
            v.setCalificacion(calificacion.byteValue());
            v.setComentarios(comentario);
            v.setFecha(LocalDateTime.now());
        } else {
            // Crea nueva
            v = new Valoracion();
            v.setUsuario(usuario);
            v.setProyecto(proyecto);
            v.setCalificacion(calificacion.byteValue());
            v.setComentarios(comentario);
            v.setFecha(LocalDateTime.now());
        }

        valoracionRespository.save(v);

        Float nuevoPromedio = valoracionRespository.obtenerPromedio(idProyecto);
        if (nuevoPromedio == null) {
            nuevoPromedio = 0f;
        }

        proyecto.setPromedioValoracion(nuevoPromedio);
        proyectoRepository.save(proyecto);

        return v;
    }

    public List<TraerValoracionDto> listarPorProyecto(Integer idProyecto) {

        Proyecto proyecto = proyectoRepository.findById(idProyecto)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        List<Valoracion> lista = valoracionRespository.findByProyecto(proyecto);

        List<TraerValoracionDto> respuesta = new ArrayList<>();


        for (Valoracion v : lista) {
            String nombre = v.getUsuario().getNombre(); // Ajusta tu campo real
            String fecha = v.getFecha().toLocalDate().toString(); // YYYY-MM-DD

            respuesta.add(new TraerValoracionDto(
                    nombre,
                    v.getCalificacion(),
                    v.getComentarios(),
                    fecha
            ));

        }

        return respuesta;
    }

}
