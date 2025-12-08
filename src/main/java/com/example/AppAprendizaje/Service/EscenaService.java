package com.example.AppAprendizaje.Service;

import com.example.AppAprendizaje.Dto.EscenaDto;
import com.example.AppAprendizaje.Model.Escena;
import java.util.List;

public interface EscenaService {
    List<Escena> guardarEscenas(List<Escena> escenas);
    Escena obtenerEscenaPorId(Integer id);
    Escena actualizarEscena(Integer id, EscenaDto escenaDto);
    List<Escena> actualizarEscenas(List<EscenaDto> escenasDto, Integer idProyecto);
}
