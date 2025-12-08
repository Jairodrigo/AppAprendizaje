package com.example.AppAprendizaje.Repository;

import com.example.AppAprendizaje.Model.Escena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscenaRepository extends JpaRepository<Escena, Integer> {
}
