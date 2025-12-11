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
@CrossOrigin(origins = "*") // Permite que React se conecte
public class AuthController {

    @Autowired
    private MaestroRepository maestroRepository;

    // Clase auxiliar para recibir los datos del JSON (DTO)
    public static class LoginRequest {
        public String email;
        public String password;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Map<String, Object> response = new HashMap<>();

        // 1. Verificación de ADMINISTRADOR (Hardcoded)
        if ("admin@escuela.com".equals(request.email) && "admin123".equals(request.password)) {
            Map<String, String> usuarioAdmin = new HashMap<>();
            usuarioAdmin.put("nombre", "Administrador");
            usuarioAdmin.put("rol", "admin");

            response.put("mensaje", "Login exitoso");
            response.put("usuario", usuarioAdmin);
            return ResponseEntity.ok(response);
        }

        // 2. Verificación de DOCENTE (Base de Datos)
        Optional<Maestro> maestroOpt = maestroRepository.findByEmail(request.email);

        if (maestroOpt.isPresent()) {
            Maestro maestro = maestroOpt.get();
            // Comparamos contraseñas (En un caso real, aquí usarías BCrypt para encriptar)
            if (maestro.getPassword() != null && maestro.getPassword().equals(request.password)) {
                
                // Preparamos los datos del usuario para enviar al frontend
                Map<String, Object> usuarioDocente = new HashMap<>();
                usuarioDocente.put("id", maestro.getId());
                usuarioDocente.put("nombre", maestro.getNombre());
                usuarioDocente.put("apellido", maestro.getApellido());
                usuarioDocente.put("email", maestro.getEmail());
                usuarioDocente.put("rol", "docente"); // <--- Importante para tu App.jsx

                response.put("mensaje", "Login exitoso");
                response.put("usuario", usuarioDocente);
                return ResponseEntity.ok(response);
            }
        }

        // Si falla
        return ResponseEntity.status(401).body(Map.of("error", "Credenciales incorrectas"));
    }
}