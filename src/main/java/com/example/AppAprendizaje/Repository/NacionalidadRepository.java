package com.example.AppAprendizaje.Repository;

import com.example.AppAprendizaje.Model.Nacionalidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NacionalidadRepository extends JpaRepository<Nacionalidad, Integer> {
    Optional<Nacionalidad> findByPais(String pais);
}
