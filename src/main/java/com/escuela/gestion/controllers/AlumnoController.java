package com.escuela.gestion.controllers;

import com.escuela.gestion.models.Alumno;
import com.escuela.gestion.repositories.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
// esto es para react se conecte y no haya problema con el navegador por los puertos diferentes
@CrossOrigin(origins = "*") 
public class AlumnoController {

    @Autowired
    private AlumnoRepository alumnoRepository;

    // traer toda la lista
    @GetMapping
    public List<Alumno> getAllAlumnos() {
        return alumnoRepository.findAll();
    }

    // dar de alta un alumno
    @PostMapping
    public Alumno createAlumno(@RequestBody Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    // editar informacion del alumno
    @PutMapping("/{id}")
    public Alumno updateAlumno(@PathVariable Long id, @RequestBody Alumno alumnoDetails) {
        return alumnoRepository.findById(id)
                .map(alumno -> {
                    // pasamos los datos nuevos al objeto que ya teniamos
                    alumno.setMatricula(alumnoDetails.getMatricula());
                    alumno.setNombre(alumnoDetails.getNombre());
                    alumno.setApellido(alumnoDetails.getApellido());
                    alumno.setDireccion(alumnoDetails.getDireccion());
                    alumno.setFechaNacimiento(alumnoDetails.getFechaNacimiento());
                    
                    return alumnoRepository.save(alumno); 
                })
                .orElse(null); // si no lo encuentra regresa nulo
    }

    // borrar del sistema
    @DeleteMapping("/{id}")
    public void deleteAlumno(@PathVariable Long id) {
        alumnoRepository.deleteById(id);
    }
}