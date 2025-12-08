package com.escuela.gestion.controllers;

import com.escuela.gestion.models.Materia;
import com.escuela.gestion.repositories.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/materias")
@CrossOrigin(origins = "*")
public class MateriaController {
    @Autowired
    private MateriaRepository materiaRepository;

    @GetMapping
    public List<Materia> getAll() {
        return materiaRepository.findAll();
    }

    @PostMapping
    public Materia create(@RequestBody Materia materia) {
        return materiaRepository.save(materia);
    }
}