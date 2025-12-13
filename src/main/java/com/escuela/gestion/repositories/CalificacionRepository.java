package com.escuela.gestion.repositories;

import com.escuela.gestion.models.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

    // buscar la nota de un alumno en especifico en esa clase regresa optional para validar facil si ya tiene o no
    // regresa optional para validar facil si ya tiene o no
    Optional<Calificacion> findByAsignacionIdAndAlumnoId(Long asignacionId, Long alumnoId);

    // traer todas las calificaciones del grupo
    List<Calificacion> findByAsignacionId(Long asignacionId);
}