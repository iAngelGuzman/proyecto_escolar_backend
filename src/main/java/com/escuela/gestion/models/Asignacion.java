package com.escuela.gestion.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "asignaciones")
public class Asignacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "maestro_id", nullable = false)
    private Maestro maestro;

    @ManyToOne
    @JoinColumn(name = "materia_id", nullable = false)
    private Materia materia;

    @ManyToOne
    @JoinColumn(name = "turno_id", nullable = false)
    private Turno turno;

    public void setMaestro(Maestro orElse) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setMateria(Materia orElse) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setTurno(Turno orElse) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}