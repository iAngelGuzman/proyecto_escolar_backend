package com.escuela.gestion.controllers;

import com.escuela.gestion.models.Maestro;
import com.escuela.gestion.repositories.MaestroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/maestros")
@CrossOrigin(origins = "*")
public class MaestroController {
    @Autowired
    private MaestroRepository maestroRepository;

    @GetMapping
    public List<Maestro> getAll() {
        return maestroRepository.findAll();
    }

    @PostMapping
    public Maestro create(@RequestBody Maestro maestro) {
        return maestroRepository.save(maestro);
    }
}