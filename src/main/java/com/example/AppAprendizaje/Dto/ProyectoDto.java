package com.example.AppAprendizaje.Dto;

import com.example.AppAprendizaje.Model.Proyecto;

import java.util.List;

//Lo usamos para recibir los datos del front
public class ProyectoDto {

    private Integer idProyecto;
    private String nombreProyecto;
    private String descripcion;
    private List<EscenaDto> escenas;
    private Proyecto.Estado estado;
    private Proyecto.TipoProyecto tipo;
    private String urlArchivo;
    private String imagenPortada;


    // Constructor vacío
    public ProyectoDto() {}

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<EscenaDto> getEscenas() {
        return escenas;
    }

    public void setEscenas(List<EscenaDto> escenas) {
        this.escenas = escenas;
    }

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Proyecto.Estado getEstado() {
        return estado;
    }

    public void setEstado(Proyecto.Estado estado) {
        this.estado = estado;
    }

    public Proyecto.TipoProyecto getTipo() {
        return tipo;
    }

    public void setTipo(Proyecto.TipoProyecto tipo) {
        this.tipo = tipo;
    }

    public String getUrlArchivo() {
        return urlArchivo;
    }

    public void setUrlArchivo(String urlArchivo) {
        this.urlArchivo = urlArchivo;
    }

    public String getImagenPortada() {
        return imagenPortada;
    }

    public void setImagenPortada(String imagenPortada) {
        this.imagenPortada = imagenPortada;
    }
}
