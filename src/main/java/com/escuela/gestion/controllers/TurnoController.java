package com.escuela.gestion.controllers;

import com.escuela.gestion.models.Turno;
import com.escuela.gestion.repositories.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/turnos")
@CrossOrigin(origins = "*")
public class TurnoController {
    @Autowired
    private TurnoRepository turnoRepository;

    // ver todos los turnos disponibles
    @GetMapping
    public List<Turno> getAll() {
        return turnoRepository.findAll();
    }

    // crear nuevo turno
    @PostMapping
    public Turno create(@RequestBody Turno turno) {
        return turnoRepository.save(turno);
    }

    // editar nombre del turno
    @PutMapping("/{id}")
    public Turno update(@PathVariable Long id, @RequestBody Turno turnoDetails) {
        return turnoRepository.findById(id)
                .map(turno -> {
                    turno.setNombreTurno(turnoDetails.getNombreTurno());
                    return turnoRepository.save(turno);
                })
                .orElse(null);
    }

    // eliminar turno
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        turnoRepository.deleteById(id);
    }
}