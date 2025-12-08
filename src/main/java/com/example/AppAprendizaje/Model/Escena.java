package com.example.AppAprendizaje.Model;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "escena")
public class Escena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_escena")
    private Integer idEscena;

    @Column(name = "color_fondo", length = 45)
    private String colorFondo;

    @Column(name = "descripcion", length = 45)
    private String descripcion;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "nombre", length = 60)
    private String nombre;

    @Column(name = "orden")
    private Byte orden;

    @ManyToOne
    @JoinColumn(name = "id_plantilla")
    private Plantilla plantilla;

    @ManyToOne
    @JoinColumn(name = "id_proyecto", nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idProyecto") //evita bucles infinitos
    @JsonIdentityReference(alwaysAsId = true) //evita bucles infinitos
    private Proyecto proyecto;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_recurso")
    private Recurso recurso;

    @OneToMany(mappedBy = "escena", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<UsoRecurso> usosRecursos;

    public Integer getIdEscena() {
        return idEscena;
    }

    public void setIdEscena(Integer idEscena) {
        this.idEscena = idEscena;
    }

    public String getColorFondo() {
        return colorFondo;
    }

    public void setColorFondo(String colorFondo) {
        this.colorFondo = colorFondo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Byte getOrden() {
        return orden;
    }

    public void setOrden(Byte orden) {
        this.orden = orden;
    }

    public Plantilla getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(Plantilla plantilla) {
        this.plantilla = plantilla;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    public List<UsoRecurso> getUsosRecursos() {
        return usosRecursos;
    }

    public void setUsosRecursos(List<UsoRecurso> usosRecursos) {
        this.usosRecursos = usosRecursos;
    }

    public Integer getIdPlantilla() {
        return plantilla != null ? plantilla.getIdPlantilla() : null;
    }

    public void setIdPlantilla(Integer idPlantilla) {
        if (idPlantilla == null) {
            this.plantilla = null;
        } else {
            Plantilla p = new Plantilla();
            p.setIdPlantilla(idPlantilla);
            this.plantilla = p;
        }
    }
}