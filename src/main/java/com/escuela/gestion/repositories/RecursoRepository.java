package com.escuela.gestion.repositories;

import com.escuela.gestion.models.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecursoRepository extends JpaRepository<Recurso, Long> {

    // traer los archivos/links de una materia en especifico
    List<Recurso> findByAsignacionId(Long asignacionId);
}