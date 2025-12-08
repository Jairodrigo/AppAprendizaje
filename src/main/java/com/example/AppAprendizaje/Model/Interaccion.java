package com.example.AppAprendizaje.Model;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@Table(name = "interaccion")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idInteraccion")
public class Interaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_interaccion")
    private Integer idInteraccion;

    @OneToOne
    @JoinColumn(name = "id_recurso", nullable = false)
    private Recurso recurso;

    @OneToOne
    @JoinColumn(name = "id_uso_recurso", unique = true, nullable = false)
    private UsoRecurso usoRecurso;

    public Integer getIdInteraccion() {
        return idInteraccion;
    }

    public void setIdInteraccion(Integer idInteraccion) {
        this.idInteraccion = idInteraccion;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    public UsoRecurso getUsoRecurso() {
        return usoRecurso;
    }

    public void setUsoRecurso(UsoRecurso usoRecurso) {
        this.usoRecurso = usoRecurso;
    }
}
