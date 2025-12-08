package com.example.AppAprendizaje.Service.Impl;

import com.example.AppAprendizaje.Dto.EscenaDto;
import com.example.AppAprendizaje.Dto.UsoRecursoDto;
import com.example.AppAprendizaje.Model.Escena;
import com.example.AppAprendizaje.Model.Proyecto;
import com.example.AppAprendizaje.Model.Recurso;
import com.example.AppAprendizaje.Model.UsoRecurso;
import com.example.AppAprendizaje.Repository.EscenaRepository;
import com.example.AppAprendizaje.Repository.ProyectoRepository;
import com.example.AppAprendizaje.Repository.RecursoRepository;
import com.example.AppAprendizaje.Repository.UsoRecursoRepository;
import com.example.AppAprendizaje.Service.EscenaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EscenaServiceImpl implements EscenaService {

    private final EscenaRepository escenaRepository;
    private final RecursoRepository recursoRepository;
    private final UsoRecursoRepository usoRecursoRepository;
    private final ProyectoRepository proyectoRepository;

    public EscenaServiceImpl(EscenaRepository escenaRepository,
                             RecursoRepository recursoRepository,
                             UsoRecursoRepository usoRecursoRepository,
                             ProyectoRepository proyectoRepository) {
        this.escenaRepository = escenaRepository;
        this.recursoRepository = recursoRepository;
        this.usoRecursoRepository = usoRecursoRepository;
        this.proyectoRepository = proyectoRepository;
    }

    @Override
    public List<Escena> guardarEscenas(List<Escena> escenas) {
        return escenaRepository.saveAll(escenas);
    }

    @Override
    public Escena obtenerEscenaPorId(Integer id) {
        return escenaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Escena no encontrada con id: " + id));
    }

    @Override
    public Escena actualizarEscena(Integer id, EscenaDto dto) {
        Escena escenaExistente = escenaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Escena no encontrada con id: " + id));

        escenaExistente.setNombre(dto.getNombre());
        escenaExistente.setDescripcion(dto.getDescripcion());
        escenaExistente.setColorFondo(dto.getColorFondo());
        escenaExistente.setOrden(dto.getOrden());
        escenaExistente.setFecha(dto.getFecha());

        // Eliminar solo los recursos que el usuario indicó
        if (dto.getRecursosEliminados() != null) {
            for (Integer idRecurso : dto.getRecursosEliminados()) {
                usoRecursoRepository.deleteByEscenaIdAndRecursoId(id, idRecurso);
            }
        }

        // Guardar o actualizar usos de recursos (no fondo)
        if (dto.getUsosRecursos() != null) {
            List<UsoRecurso> nuevosUsos = new ArrayList<>();
            for (UsoRecursoDto urDto : dto.getUsosRecursos()) {

                Recurso recurso = null;
                if (urDto.getIdRecurso() != null) {
                    recurso = recursoRepository.findById(urDto.getIdRecurso())
                            .orElseThrow(() -> new RuntimeException("Recurso no encontrado con id: " + urDto.getIdRecurso()));
                } else if (urDto.getNombreRecurso() != null) {
                    List<Recurso> recursos = recursoRepository.findByNombreRecurso(urDto.getNombreRecurso());
                    if (recursos.isEmpty()) {
                        throw new RuntimeException("Recurso no encontrado con nombre: " + urDto.getNombreRecurso());
                    }
                    recurso = recursos.get(0); // toma el primero o ajusta según tu lógica
                }


                if (recurso != null) {
                    UsoRecurso nuevoUso = new UsoRecurso();
                    nuevoUso.setEscena(escenaExistente);
                    nuevoUso.setRecurso(recurso);
                    nuevoUso.setAlto(urDto.getAlto());
                    nuevoUso.setAncho(urDto.getAncho());
                    nuevoUso.setX(urDto.getX());
                    nuevoUso.setY(urDto.getY());
                    nuevosUsos.add(nuevoUso);
                }
            }
            usoRecursoRepository.saveAll(nuevosUsos);
        }
        // Eliminación del fondo
        if (dto.isFondoEliminado()) {
            List<Recurso> fondos = recursoRepository.findByNombreRecurso("Fondo");
            for (Recurso fondo : fondos) {
                usoRecursoRepository.deleteByEscenaIdAndRecursoId(id, fondo.getIdRecurso());
            }

            escenaExistente.setColorFondo(null);
        }

        return escenaRepository.save(escenaExistente);
    }

    @Override
    public List<Escena> actualizarEscenas(List<EscenaDto> escenasDto, Integer idProyecto) {
        Proyecto proyecto = proyectoRepository.findById(idProyecto)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con id: " + idProyecto));

        List<Escena> escenasActualizadas = new ArrayList<>();
        for (EscenaDto dto : escenasDto) {
            if (dto.getIdEscena() != null) {
                // Actualiza escena existente
                escenasActualizadas.add(actualizarEscena(dto.getIdEscena(), dto));
            } else {
                // Crea nueva escena
                Escena nueva = new Escena();
                nueva.setNombre(dto.getNombre());
                nueva.setColorFondo(dto.getColorFondo());
                nueva.setDescripcion(dto.getDescripcion());
                nueva.setOrden(dto.getOrden());
                nueva.setFecha(dto.getFecha());
                nueva.setProyecto(proyecto);
                Escena guardada = escenaRepository.save(nueva);

                // Crear usos de recursos asociados
                if (dto.getUsosRecursos() != null) {
                    List<UsoRecurso> nuevosUsos = new ArrayList<>();
                    for (UsoRecursoDto urDto : dto.getUsosRecursos()) {
                        Recurso recurso = null;
                        if (urDto.getIdRecurso() != null) {
                            recurso = recursoRepository.findById(urDto.getIdRecurso())
                                    .orElseThrow(() -> new RuntimeException("Recurso no encontrado con id: " + urDto.getIdRecurso()));
                        } else if (urDto.getNombreRecurso() != null) {
                            List<Recurso> recursos = recursoRepository.findByNombreRecurso(urDto.getNombreRecurso());
                            if (recursos.isEmpty()) {
                                throw new RuntimeException("Recurso no encontrado con nombre: " + urDto.getNombreRecurso());
                            }
                            recurso = recursos.get(0); // toma el primero
                        }


                        UsoRecurso nuevoUso = new UsoRecurso();
                        nuevoUso.setEscena(guardada);
                        nuevoUso.setRecurso(recurso);
                        nuevoUso.setAlto(urDto.getAlto());
                        nuevoUso.setAncho(urDto.getAncho());
                        nuevoUso.setX(urDto.getX());
                        nuevoUso.setY(urDto.getY());

                        nuevosUsos.add(nuevoUso);
                    }
                    usoRecursoRepository.saveAll(nuevosUsos);
                }

                escenasActualizadas.add(guardada);
            }
        }
        return escenasActualizadas;
    }

}
