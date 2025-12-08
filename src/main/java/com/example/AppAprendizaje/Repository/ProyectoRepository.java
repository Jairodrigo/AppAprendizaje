package com.example.AppAprendizaje.Repository;

import com.example.AppAprendizaje.Model.Proyecto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
    List<Proyecto> findByUsuarioIdUsuario(Short usuarioId); //buscar proyectos por usuario
    List<Proyecto> findByTipo(Proyecto.TipoProyecto tipo); //buscar proyectos por tipo de proyectos
    @EntityGraph(attributePaths = {
            "escenas",
            "escenas.usosRecursos",
            "escenas.usosRecursos.recurso",
            "escenas.usosRecursos.interaccion"
    })
    Optional<Proyecto> findWithEscenasByIdProyecto(Integer idProyecto);
}
