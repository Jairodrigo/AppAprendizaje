package com.example.AppAprendizaje.Dto;

import lombok.Data;

@Data
public class ProyectoPublicoDto {
    private Integer idProyecto;
    private String nombreProyecto;
    private String descripcion;
    private String imagenPortada;

    private String nombreUsuario;

    private Float promedioValoracion;

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

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

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Float getPromedioValoracion() {
        return promedioValoracion;
    }

    public void setPromedioValoracion(Float promedioValoracion) {
        this.promedioValoracion = promedioValoracion;
    }

    public String getImagenPortada() {
        return imagenPortada;
    }

    public void setImagenPortada(String imagenPortada) {
        this.imagenPortada = imagenPortada;
    }
}
