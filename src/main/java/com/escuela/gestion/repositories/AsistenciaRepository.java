package com.escuela.gestion.repositories;

import com.escuela.gestion.models.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    // cargar la asistencia de un dia completo
    List<Asistencia> findByAsignacionIdAndFecha(Long asignacionId, LocalDate fecha);

    // buscar si un alumno ya tiene asistencia ese dia (para validar duplicados)
    Optional<Asistencia> findByAsignacionIdAndAlumnoIdAndFecha(Long asignacionId, Long alumnoId, LocalDate fecha);
    
    // reporte por rango de fechas para historial de asistencia uso between y orderby para que ya salgan ordenadas y no batallar en el front
    List<Asistencia> findByAsignacionIdAndFechaBetweenOrderByFechaAsc(Long asignacionId, LocalDate inicio, LocalDate fin);
}