package com.example.AppAprendizaje.Repository;

import com.example.AppAprendizaje.Model.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecursoRepository extends JpaRepository<Recurso, Integer> {
    List<Recurso> findByNombreRecurso(String nombreRecurso);

}
