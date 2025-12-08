package com.escuela.gestion.controllers;

import com.escuela.gestion.models.*;
import com.escuela.gestion.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/asignaciones")
@CrossOrigin(origins = "*")
public class AsignacionController {

    @Autowired private AsignacionRepository asignacionRepository;
    @Autowired private MaestroRepository maestroRepository;
    @Autowired private MateriaRepository materiaRepository;
    @Autowired private TurnoRepository turnoRepository;

    @GetMapping
    public List<Asignacion> getAll() {
        return asignacionRepository.findAll();
    }

    // Recibimos un mapa con los IDs
    @PostMapping
    public Asignacion create(@RequestBody Map<String, Integer> payload) {
        Asignacion nueva = new Asignacion();

        // Convertimos los IDs (Integer) a Long y buscamos las entidades
        Long maestroId = Long.valueOf(payload.get("maestro_id"));
        Long materiaId = Long.valueOf(payload.get("materia_id"));
        Long turnoId = Long.valueOf(payload.get("turno_id"));

        nueva.setMaestro(maestroRepository.findById(maestroId).orElse(null));
        nueva.setMateria(materiaRepository.findById(materiaId).orElse(null));
        nueva.setTurno(turnoRepository.findById(turnoId).orElse(null));

        return asignacionRepository.save(nueva);
    }
}