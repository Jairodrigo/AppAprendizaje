package com.example.AppAprendizaje.Model;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "plantilla")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idPlantilla")
public class Plantilla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plantilla")
    private Integer idPlantilla;

    @Column(name = "datos", length = 255)
    private String datos;

    @Column(name = "nombre_plantilla", length = 60)
    private String nombrePlantilla;

    @OneToMany(mappedBy = "plantilla")
    private List<Escena> escenas;

    public Integer getIdPlantilla() {
        return idPlantilla;
    }

    public void setIdPlantilla(Integer idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public String getNombrePlantilla() {
        return nombrePlantilla;
    }

    public void setNombrePlantilla(String nombrePlantilla) {
        this.nombrePlantilla = nombrePlantilla;
    }

    public List<Escena> getEscenas() {
        return escenas;
    }

    public void setEscenas(List<Escena> escenas) {
        this.escenas = escenas;
    }
}