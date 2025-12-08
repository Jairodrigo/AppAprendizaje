package com.example.AppAprendizaje.Dto;

import com.example.AppAprendizaje.Model.Recurso;

public class UsoRecursoDto {

    private Integer idUsoRecurso;
    private Double x;
    private Double y;
    private Double ancho;
    private Double alto;
    private Integer idEscena;
    private Integer idRecurso;
    private String rutaImagen;
    private String nombreRecurso;
    private String forma;

    // Reacción asociada
    private Reaccion reaccion;

    // Getters y Setters
    public Integer getIdUsoRecurso() { return idUsoRecurso; }
    public void setIdUsoRecurso(Integer idUsoRecurso) { this.idUsoRecurso = idUsoRecurso; }

    public Double getX() { return x; }
    public void setX(Double x) { this.x = x; }

    public Double getY() { return y; }
    public void setY(Double y) { this.y = y; }

    public Double getAncho() { return ancho; }
    public void setAncho(Double ancho) { this.ancho = ancho; }

    public Double getAlto() { return alto; }
    public void setAlto(Double alto) { this.alto = alto; }

    public Integer getIdEscena() { return idEscena; }
    public void setIdEscena(Integer idEscena) { this.idEscena = idEscena; }

    public Integer getIdRecurso() { return idRecurso; }
    public void setIdRecurso(Integer idRecurso) { this.idRecurso = idRecurso; }

    public String getRutaImagen() { return rutaImagen; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }

    public String getNombreRecurso() { return nombreRecurso; }
    public void setNombreRecurso(String nombreRecurso) { this.nombreRecurso = nombreRecurso; }

    public Reaccion getReaccion() { return reaccion; }
    public void setReaccion(Reaccion reaccion) { this.reaccion = reaccion; }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    // Clase interna para reacción
    public static class Reaccion {
        private Integer idRecurso;
        private String nombreRecurso;
        private Recurso.TipoRecurso tipoRecurso;
        private String url;

        public String getNombreRecurso() { return nombreRecurso; }
        public void setNombreRecurso(String nombreRecurso) { this.nombreRecurso = nombreRecurso; }

        public Recurso.TipoRecurso getTipoRecurso() { return tipoRecurso; }
        public void setTipoRecurso(Recurso.TipoRecurso tipoRecurso) { this.tipoRecurso = tipoRecurso; }

        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }

        public Integer getIdRecurso() {
            return idRecurso;
        }

        public void setIdRecurso(Integer idRecurso) {
            this.idRecurso = idRecurso;
        }
    }
}
