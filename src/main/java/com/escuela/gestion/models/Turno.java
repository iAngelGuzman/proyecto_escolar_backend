package com.escuela.gestion.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "turnos")
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_turno", nullable = false, unique = true)
    private String nombreTurno;
}