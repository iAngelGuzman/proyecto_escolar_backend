package com.escuela.gestion.controllers;

import com.escuela.gestion.models.Alumno;
import com.escuela.gestion.repositories.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
@CrossOrigin(origins = "*") //Permite que React se conecte
public class AlumnoController {

    @Autowired
    private AlumnoRepository alumnoRepository;

    // GET: Obtener todos los alumnos
    @GetMapping
    public List<Alumno> getAllAlumnos() {
        return alumnoRepository.findAll();
    }

    // POST: Guardar un alumno
    @PostMapping
    public Alumno createAlumno(@RequestBody Alumno alumno) {
        return alumnoRepository.save(alumno);
    }
}