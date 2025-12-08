package com.example.AppAprendizaje.Repository;

import com.example.AppAprendizaje.Model.UsoRecurso;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsoRecursoRepository extends JpaRepository<UsoRecurso, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM UsoRecurso ur WHERE ur.escena.idEscena = :idEscena AND ur.recurso.idRecurso = :idRecurso")
    void deleteByEscenaIdAndRecursoId(@Param("idEscena") Integer idEscena, @Param("idRecurso") Integer idRecurso);
}
