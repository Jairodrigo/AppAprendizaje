package com.example.AppAprendizaje.Model;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "proyecto")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto")
    private Integer idProyecto;

    @Column(name = "nombre_proyecto", nullable = false, length = 100)
    private String nombreProyecto;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Column(name = "fecha_proyecto", nullable = false)
    private LocalDateTime fechaProyecto;

    @Column(name = "promedio_valoracion")
    private Float promedioValoracion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoProyecto tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @Column(name = "imagen_portada", length = 50)
    private String imagenPortada;

    @Column(name = "url_archivo", length = 100)
    private String urlArchivo;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonBackReference("usuario-proyecto") //evita bucles infinitos al convertir los obj a Json
    private Usuario usuario;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Escena> escenas;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    @JsonManagedReference("proyecto-valoracion") //evita bucles infinitos al convertir los obj a Json
    private List<Valoracion> valoraciones;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    @JsonManagedReference("proyecto-favoritos") //evita bucles infinitos al convertir los obj a Json
    private List<Favorito> favoritos;


    @Transient
    private boolean esFavorito;

    public enum TipoProyecto {
        PB, PV
    }
    public enum Estado {
        B, C
    }

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

    public LocalDateTime getFechaProyecto() {
        return fechaProyecto;
    }

    public void setFechaProyecto(LocalDateTime fechaProyecto) {
        this.fechaProyecto = fechaProyecto;
    }

    public Float getPromedioValoracion() {
        return promedioValoracion;
    }

    public void setPromedioValoracion(Float promedioValoracion) {
        this.promedioValoracion = promedioValoracion;
    }

    public TipoProyecto getTipo() {
        return tipo;
    }

    public void setTipo(TipoProyecto tipo) {
        this.tipo = tipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Escena> getEscenas() {
        return escenas;
    }

    public void setEscenas(List<Escena> escenas) {
        this.escenas = escenas;
    }

    public List<Valoracion> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(List<Valoracion> valoraciones) {
        this.valoraciones = valoraciones;
    }

    public List<Favorito> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Favorito> favoritos) {
        this.favoritos = favoritos;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public boolean isEsFavorito() {
        return esFavorito;
    }

    public void setEsFavorito(boolean esFavorito) {
        this.esFavorito = esFavorito;
    }

    public String getImagenPortada() {
        return imagenPortada;
    }

    public void setImagenPortada(String imagenPortada) {
        this.imagenPortada = imagenPortada;
    }

    public String getUrlArchivo() {
        return urlArchivo;
    }

    public void setUrlArchivo(String urlArchivo) {
        this.urlArchivo = urlArchivo;
    }
}

