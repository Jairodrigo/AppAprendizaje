package com.example.AppAprendizaje.Dto;

import com.example.AppAprendizaje.Model.Recurso;

public class RecursoDto {

    private String nombreRecurso;
    private Recurso.TipoRecurso tipoRecurso;
    private String urlArchivo;

    public String getNombreRecurso() {
        return nombreRecurso;
    }

    public void setNombreRecurso(String nombreRecurso) {
        this.nombreRecurso = nombreRecurso;
    }

    public Recurso.TipoRecurso getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(Recurso.TipoRecurso tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    public String getUrlArchivo() {
        return urlArchivo;
    }

    public void setUrlArchivo(String urlArchivo) {
        this.urlArchivo = urlArchivo;
    }
}
