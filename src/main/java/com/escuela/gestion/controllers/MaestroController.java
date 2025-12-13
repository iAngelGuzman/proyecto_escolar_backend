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

    // traer lista de maestros
    @GetMapping
    public List<Maestro> getAll() {
        return maestroRepository.findAll();
    }

    // registrar uno nuevo
    @PostMapping
    public Maestro create(@RequestBody Maestro maestro) {
        return maestroRepository.save(maestro);
    }

    // editar datos 
    @PutMapping("/{id}")
    public Maestro update(@PathVariable Long id, @RequestBody Maestro maestroDetails) {
        return maestroRepository.findById(id)
                .map(maestro -> {
                    maestro.setNombre(maestroDetails.getNombre());
                    maestro.setApellido(maestroDetails.getApellido());
                    maestro.setEmail(maestroDetails.getEmail());
                    maestro.setTelefono(maestroDetails.getTelefono());
                    
                    // si viene contraseña nueva la cambiamos, si no la dejamos igual
                    if (maestroDetails.getPassword() != null && !maestroDetails.getPassword().isEmpty()) {
                        maestro.setPassword(maestroDetails.getPassword());
                    }
                    return maestroRepository.save(maestro);
                })
                .orElse(null);
    }

    // borrar del sistema
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        maestroRepository.deleteById(id);
    }
}