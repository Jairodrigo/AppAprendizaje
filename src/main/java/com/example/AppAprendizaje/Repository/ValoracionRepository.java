package com.example.AppAprendizaje.Repository;

import com.example.AppAprendizaje.Model.Proyecto;
import com.example.AppAprendizaje.Model.Usuario;
import com.example.AppAprendizaje.Model.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ValoracionRepository extends JpaRepository<Valoracion, Integer> {
    Optional<Valoracion> findByUsuarioAndProyecto(Usuario usuario, Proyecto proyecto);

    // Listar valoraciones de un proyecto
    List<Valoracion> findByProyecto(Proyecto proyecto);

    @Query("SELECT AVG(v.calificacion) FROM Valoracion v WHERE v.proyecto.id = :idProyecto")
    Float obtenerPromedio(Integer idProyecto);


}
