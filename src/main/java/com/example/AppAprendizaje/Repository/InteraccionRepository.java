package com.example.AppAprendizaje.Repository;

import com.example.AppAprendizaje.Model.Interaccion;
import com.example.AppAprendizaje.Model.UsoRecurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InteraccionRepository extends JpaRepository<Interaccion, Integer> {
    List<Interaccion> findByUsoRecursoIdUsoRecurso(Integer idUsoRecurso);

    Optional<Interaccion> findByUsoRecurso(UsoRecurso usoRecurso);

    boolean existsByRecurso_IdRecurso(Integer id);

    @Query("SELECT i FROM Interaccion i WHERE i.usoRecurso.idUsoRecurso = :idUsoRecurso")
    Interaccion findByUsoRecursoId(@Param("idUsoRecurso") Integer idUsoRecurso);

    @Query("SELECT i FROM Interaccion i WHERE i.recurso.idRecurso = :idRecurso")
    List<Interaccion> findByRecursoId(@Param("idRecurso") Integer idRecurso);
}
