package com.escuela.gestion.models;

import jakarta.persistence.*;
import lombok.Data; // Lombok genera getters y setters automáticamente
import java.time.LocalDate;

@Data // Esto crea getters, setters, toString, etc.
@Entity
@Table(name = "alumnos")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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