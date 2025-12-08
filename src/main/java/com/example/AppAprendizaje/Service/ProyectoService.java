package com.example.AppAprendizaje.Service;

import com.example.AppAprendizaje.Dto.ProyectoDto;
import com.example.AppAprendizaje.Dto.ProyectoPublicoDto;
import com.example.AppAprendizaje.Model.Proyecto;
import java.util.List;
import java.util.Optional;

//Definicion de los métodos del servicio
public interface ProyectoService {
    Optional<Proyecto> obtenerProyectoPorId(Integer id);
    List<Proyecto> listarProyectosPorUsuario(Short usuarioId);
    List<ProyectoPublicoDto>obtenerProyectosPublicos();
    ProyectoDto getProyectoCompleto(Integer idProyecto);
}
