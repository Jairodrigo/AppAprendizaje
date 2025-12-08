package com.example.AppAprendizaje.Dto;

public class TraerValoracionDto {

    private String usuarioNombre;
    private byte calificacion;
    private String comentarios;
    private String fecha;

    public TraerValoracionDto(String usuarioNombre, byte calificacion, String comentarios, String fecha) {
        this.usuarioNombre = usuarioNombre;
        this.calificacion = calificacion;
        this.comentarios = comentarios;
        this.fecha = fecha;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public byte getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(byte calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
