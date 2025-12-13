package com.escuela.gestion.controllers;

import com.escuela.gestion.models.*;
import com.escuela.gestion.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/asignaciones")
@CrossOrigin(origins = "*")
public class AsignacionController {

    @Autowired private AsignacionRepository asignacionRepository;
    @Autowired private MaestroRepository maestroRepository;
    @Autowired private MateriaRepository materiaRepository;
    @Autowired private TurnoRepository turnoRepository;

    // traer todas las asignaciones
    @GetMapping
    public List<Asignacion> getAll() {
        return asignacionRepository.findAll();
    }

    // hacer alta de nueva asignacion, usamos map para recibir datos sin hacer otra clase
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> payload) { 
        try {
            Asignacion nueva = new Asignacion();
            return guardarAsignacion(nueva, payload);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear: " + e.getMessage());
        }
    }

    // modificar asignacion
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        Optional<Asignacion> asignacionOpt = asignacionRepository.findById(id);

        if (asignacionOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            return guardarAsignacion(asignacionOpt.get(), payload);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar: " + e.getMessage());
        }
    }

    // eliminar registro
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!asignacionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        asignacionRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // obtener lista de alumnos en esta asignacion
    @GetMapping("/{id}/alumnos")
    public ResponseEntity<?> getAlumnosDeAsignacion(@PathVariable Long id) {
        Optional<Asignacion> asignacionOpt = asignacionRepository.findById(id);
        if (asignacionOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(asignacionOpt.get().getAlumnos());
    }

    // metodo auxiliar para no repetir codigo en create y update
    private ResponseEntity<?> guardarAsignacion(Asignacion asignacion, Map<String, Object> payload) {
        // conversion manual de ids para evitar errores de tipo
        Long maestroId = convertToLong(payload.get("maestro_id"));
        Long materiaId = convertToLong(payload.get("materia_id"));
        Long turnoId = convertToLong(payload.get("turno_id"));

        if (maestroId == null || materiaId == null || turnoId == null) {
            return ResponseEntity.badRequest().body("Faltan datos obligatorios");
        }

        // validar que existan en la base de datos
        Optional<Maestro> maestroOpt = maestroRepository.findById(maestroId);
        Optional<Materia> materiaOpt = materiaRepository.findById(materiaId);
        Optional<Turno> turnoOpt = turnoRepository.findById(turnoId);

        if (maestroOpt.isEmpty() || materiaOpt.isEmpty() || turnoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Datos invalidos (maestro, materia o turno no existen)");
        }

        // actualizar relaciones
        asignacion.setMaestro(maestroOpt.get());
        asignacion.setMateria(materiaOpt.get());
        asignacion.setTurno(turnoOpt.get());

        Asignacion saved = asignacionRepository.save(asignacion);
        return ResponseEntity.ok(saved);
    }

    // convertir numeros del json a long
    private Long convertToLong(Object o) {
        if (o instanceof Number) return ((Number) o).longValue();
        if (o instanceof String) return Long.parseLong((String) o);
        return null;
    }
}