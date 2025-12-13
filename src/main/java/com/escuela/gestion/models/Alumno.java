package com.escuela.gestion.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

// usamos lombok para no escribir mil getters y setters a mano
@Data 
@Entity
@Table(name = "alumnos")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // la matricula no se puede repetir
    @Column(nullable = false, unique = true)
    private String matricula;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    private String direccion;
}