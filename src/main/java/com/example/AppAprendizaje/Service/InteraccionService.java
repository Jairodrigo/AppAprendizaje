package com.example.AppAprendizaje.Service;

import com.example.AppAprendizaje.Model.Interaccion;
import com.example.AppAprendizaje.Model.Recurso;
import com.example.AppAprendizaje.Model.UsoRecurso;
import com.example.AppAprendizaje.Repository.InteraccionRepository;
import com.example.AppAprendizaje.Repository.RecursoRepository;
import com.example.AppAprendizaje.Repository.UsoRecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InteraccionService {

    @Autowired
    private UsoRecursoRepository usoRecursoRepo;

    @Autowired
    private RecursoRepository recursoRepo;

    @Autowired
    private InteraccionRepository interaccionRepo;

    public Interaccion crearInteraccion(Integer idUsoRecurso, Integer idRecurso) {

        UsoRecurso uso = usoRecursoRepo.findById(idUsoRecurso)
                .orElseThrow(() -> new RuntimeException("UsoRecurso no encontrado"));

        Recurso recurso = recursoRepo.findById(idRecurso)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));

        Interaccion interaccion = new Interaccion();
        interaccion.setUsoRecurso(uso);
        interaccion.setRecurso(recurso);

        return interaccionRepo.save(interaccion);
    }
}

