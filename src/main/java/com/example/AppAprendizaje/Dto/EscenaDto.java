package com.example.AppAprendizaje.Dto;

import java.time.LocalDateTime;
import java.util.List;

public class EscenaDto {
    private Integer idEscena;
    private String colorFondo;
    private String descripcion;
    private LocalDateTime fecha;
    private String nombre;
    private Byte orden;
    private Integer idPlantilla;
    private Integer idProyecto;
    private List<UsoRecursoDto> usosRecursos;
    private List<Integer> recursosEliminados;


    private boolean fondoEliminado;

    public boolean isFondoEliminado() {
        return fondoEliminado;
    }

    public void setFondoEliminado(boolean fondoEliminado) {
        this.fondoEliminado = fondoEliminado;
    }
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
    public Integer getIdPlantilla() {
        return idPlantilla;
    }
    public void setIdPlantilla(Integer idPlantilla) {
        this.idPlantilla = idPlantilla;
    }
    public Integer getIdProyecto() {
        return idProyecto;
    }
    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public List<UsoRecursoDto> getUsosRecursos() {
        return usosRecursos;
    }

    public void setUsosRecursos(List<UsoRecursoDto> usosRecursos) {
        this.usosRecursos = usosRecursos;
    }

    public List<Integer> getRecursosEliminados() {
        return recursosEliminados;
    }

    public void setRecursosEliminados(List<Integer> recursosEliminados) {
        this.recursosEliminados = recursosEliminados;
    }
}
