package com.escuela.gestion.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

// esta tabla une maestro + materia + turno
@Data
@Entity
@Table(name = "asignaciones")
public class Asignacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // relaciones con catalogos
    @ManyToOne
    @JoinColumn(name = "maestro_id", nullable = false)
    private Maestro maestro;

    @ManyToOne
    @JoinColumn(name = "materia_id", nullable = false)
    private Materia materia;

    @ManyToOne
    @JoinColumn(name = "turno_id", nullable = false)
    private Turno turno;

    // tabla dinamica para los alumnos inscritos en este grupo
    @ManyToMany
    @JoinTable(
        name = "asignacion_alumnos",
        joinColumns = @JoinColumn(name = "asignacion_id"),
        inverseJoinColumns = @JoinColumn(name = "alumno_id")
    )
    private List<Alumno> alumnos;
}