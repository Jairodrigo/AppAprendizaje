package com.example.AppAprendizaje.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "recurso")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idRecurso")
public class Recurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recurso")
    private Integer idRecurso;

    @Column(name = "imagen_fondo", length = 50)
    private String imagenFondo;

    @Column(name = "nombre_recurso", nullable = false, length = 100)
    private String nombreRecurso;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_recurso", nullable = false)
    private TipoRecurso tipoRecurso;

    @Column(name = "url_archivo", length = 150)
    private String urlArchivo;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Recurso parent;

    @OneToMany(mappedBy = "parent")
    private List<Recurso> subRecursos;

    @OneToMany(mappedBy = "recurso")
    private List<Escena> escenas;

    @OneToMany(mappedBy = "recurso")
    private List<UsoRecurso> usosRecursos;

    @OneToMany(mappedBy = "recurso")
    private List<Interaccion> interacciones;

    public enum TipoRecurso {
        AUDIO, IMAGEN
    }

    public Integer getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(Integer idRecurso) {
        this.idRecurso = idRecurso;
    }

    public String getImagenFondo() {
        return imagenFondo;
    }

    public void setImagenFondo(String imagenFondo) {
        this.imagenFondo = imagenFondo;
    }

    public String getNombreRecurso() {
        return nombreRecurso;
    }

    public void setNombreRecurso(String nombreRecurso) {
        this.nombreRecurso = nombreRecurso;
    }

    public TipoRecurso getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(TipoRecurso tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    public String getUrlArchivo() {
        return urlArchivo;
    }

    public void setUrlArchivo(String urlArchivo) {
        this.urlArchivo = urlArchivo;
    }

    public Recurso getParent() {
        return parent;
    }

    public void setParent(Recurso parent) {
        this.parent = parent;
    }

    public List<Recurso> getSubRecursos() {
        return subRecursos;
    }

    public void setSubRecursos(List<Recurso> subRecursos) {
        this.subRecursos = subRecursos;
    }

    public List<Escena> getEscenas() {
        return escenas;
    }

    public void setEscenas(List<Escena> escenas) {
        this.escenas = escenas;
    }

    public List<UsoRecurso> getUsosRecursos() {
        return usosRecursos;
    }

    public void setUsosRecursos(List<UsoRecurso> usosRecursos) {
        this.usosRecursos = usosRecursos;
    }

    public List<Interaccion> getInteracciones() {
        return interacciones;
    }

    public void setInteracciones(List<Interaccion> interacciones) {
        this.interacciones = interacciones;
    }
}