package com.escuela.gestion.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "maestros")
public class Maestro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
}