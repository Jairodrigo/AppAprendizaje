package com.example.AppAprendizaje.Model;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@Table(name = "usorecurso")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idUsoRecurso")
public class UsoRecurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_uso_recurso")
    private Integer idUsoRecurso;

    @Column(name = "alto")
    private Double alto;

    @Column(name = "ancho")
    private Double ancho;

    @Column(name = "x")
    private Double x;

    @Column(name = "y")
    private Double y;

    public enum Forma {
        CUADRADO, REDONDO, CIRCULO, OVALO
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "forma")
    private Forma forma = Forma.CUADRADO;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_escena", nullable = false)
    private Escena escena;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_recurso", nullable = false)
    private Recurso recurso;

    @OneToOne(mappedBy = "usoRecurso", cascade = CascadeType.ALL, orphanRemoval = true)
    private Interaccion interaccion;

    public Integer getIdUsoRecurso() {
        return idUsoRecurso;
    }

    public void setIdUsoRecurso(Integer idUsoRecurso) {
        this.idUsoRecurso = idUsoRecurso;
    }

    public Double getAlto() {
        return alto;
    }

    public void setAlto(Double alto) {
        this.alto = alto;
    }

    public Double getAncho() {
        return ancho;
    }

    public void setAncho(Double ancho) {
        this.ancho = ancho;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Escena getEscena() {
        return escena;
    }

    public void setEscena(Escena escena) {
        this.escena = escena;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    public Interaccion getInteraccion() {
        return interaccion;
    }

    public void setInteraccion(Interaccion interaccion) {
        this.interaccion = interaccion;
    }

    public Forma getForma() {
        return forma;
    }

    public void setForma(Forma forma) {
        this.forma = forma;
    }
}