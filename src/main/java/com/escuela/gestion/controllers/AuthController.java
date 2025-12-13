package com.escuela.gestion.controllers;

import com.escuela.gestion.models.Maestro;
import com.escuela.gestion.repositories.MaestroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "*") // permite conexion desde react
public class AuthController {

    @Autowired
    private MaestroRepository maestroRepository;

    // clase auxiliar para recibir los datos del json mas facil
    public static class LoginRequest {
        public String email;
        public String password;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Map<String, Object> response = new HashMap<>();

        // 1. verificacion de admin (quemado en codigo por rapidez)
        if ("admin@escuela.com".equals(request.email) && "admin123".equals(request.password)) {
            Map<String, String> usuarioAdmin = new HashMap<>();
            usuarioAdmin.put("nombre", "Administrador");
            usuarioAdmin.put("rol", "admin");

            response.put("mensaje", "Login exitoso");
            response.put("usuario", usuarioAdmin);
            return ResponseEntity.ok(response);
        }

        // 2. verificacion de docente en base de datos
        Optional<Maestro> maestroOpt = maestroRepository.findByEmail(request.email);

        if (maestroOpt.isPresent()) {
            Maestro maestro = maestroOpt.get();
            // comparamos contraseñas directo (sin hash por ahora)
            if (maestro.getPassword() != null && maestro.getPassword().equals(request.password)) {
                
                // preparamos los datos para el front
                Map<String, Object> usuarioDocente = new HashMap<>();
                usuarioDocente.put("id", maestro.getId());
                usuarioDocente.put("nombre", maestro.getNombre());
                usuarioDocente.put("apellido", maestro.getApellido());
                usuarioDocente.put("email", maestro.getEmail());
                usuarioDocente.put("rol", "docente"); // importante para saber que menu mostrar

                response.put("mensaje", "Login exitoso");
                response.put("usuario", usuarioDocente);
                return ResponseEntity.ok(response);
            }
        }

        // si falla
        return ResponseEntity.status(401).body(Map.of("error", "Credenciales incorrectas"));
    }
}