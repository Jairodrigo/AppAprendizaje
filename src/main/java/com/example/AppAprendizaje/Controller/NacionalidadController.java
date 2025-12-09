package com.example.AppAprendizaje.Controller;

import com.example.AppAprendizaje.Model.Nacionalidad;
import com.example.AppAprendizaje.Repository.NacionalidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/nacionalidades")
@CrossOrigin(origins = "https://app-aprendizaje-front.vercel.app")
public class NacionalidadController {

    @Autowired
    private NacionalidadRepository nacionalidadRepository;

    @GetMapping
    public List<Nacionalidad> listar() {
        return nacionalidadRepository.findAll();
    }
}
