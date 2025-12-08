package com.example.AppAprendizaje.Controller;

import com.example.AppAprendizaje.Dto.TraerValoracionDto;
import com.example.AppAprendizaje.Dto.ValoracionDto;
import com.example.AppAprendizaje.Model.Valoracion;
import com.example.AppAprendizaje.Service.ValoracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/valoraciones")
@CrossOrigin(origins = "http://localhost:4200")
public class ValoracionController {

    @Autowired
    private ValoracionService valoracionService;

    @PostMapping("/guardar")
    public Valoracion guardar(@RequestBody ValoracionDto request) {
        return valoracionService.guardarValoracion(
                request.getIdUsuario(),
                request.getIdProyecto(),
                request.getCalificacion(),
                request.getComentarios()
        );
    }

    @GetMapping("/proyecto/{idProyecto}")
    public List<TraerValoracionDto> listarPorProyecto(@PathVariable Integer idProyecto) {
        return valoracionService.listarPorProyecto(idProyecto);
    }


}

