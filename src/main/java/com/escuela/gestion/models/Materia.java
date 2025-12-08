package com.escuela.gestion.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "materias")
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_materia", nullable = false)
    private String nombreMateria;

    @Column(name = "clave_materia", unique = true, nullable = false)
    private String claveMateria;

    private String descripcion;
}