package com.example.AppAprendizaje.Service;

import com.example.AppAprendizaje.Model.Nacionalidad;
import com.example.AppAprendizaje.Repository.NacionalidadRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class NacionalidadService {

    private final NacionalidadRepository nacionalidadRepository;

    @Autowired
    public NacionalidadService(NacionalidadRepository nacionalidadRepository) {
        this.nacionalidadRepository = nacionalidadRepository;
    }

    public List<Nacionalidad> findAll() {
        return nacionalidadRepository.findAll();
    }

}