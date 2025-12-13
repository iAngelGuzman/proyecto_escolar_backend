package com.escuela.gestion.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;

// registro diario de faltas y asistencias
@Entity
@Table(name = "asistencias")
public class Asistencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    // valores posibles: presente, ausente, tardanza
    private String status; 

    // relacion con alumno sin esto explota al convertir a json por el lazy loading (bucle infinito)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumno_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    private Alumno alumno;

    // a que clase pertenece la asistencia
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asignacion_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    private Asignacion asignacion;

    // getters y setters (lombok a veces da lata con relaciones lazy)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Alumno getAlumno() { return alumno; }
    public void setAlumno(Alumno alumno) { this.alumno = alumno; }
    public Asignacion getAsignacion() { return asignacion; }
    public void setAsignacion(Asignacion asignacion) { this.asignacion = asignacion; }
}