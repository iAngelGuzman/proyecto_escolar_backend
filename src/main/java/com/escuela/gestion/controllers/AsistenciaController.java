package com.escuela.gestion.controllers;

import com.escuela.gestion.models.*;
import com.escuela.gestion.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") 
public class AsistenciaController {

    @Autowired private AsistenciaRepository asistenciaRepo;
    @Autowired private AsignacionRepository asignacionRepo;
    @Autowired private AlumnoRepository alumnoRepo;

    // metodo auxiliar para limpiar fechas
    // react a veces manda la hora completa y java explota, asi que cortamos el string
    private LocalDate parsearFechaSegura(String fechaStr) {
        if (fechaStr.length() > 10) {
            fechaStr = fechaStr.substring(0, 10);
        }
        return LocalDate.parse(fechaStr);
    }

    // cargar la lista de asistencia de un dia especifico
    @GetMapping("/asignaciones/{id}/asistencia")
    public ResponseEntity<?> getAsistencia(@PathVariable Long id, @RequestParam String fecha) {
        try {
            LocalDate localDate = parsearFechaSegura(fecha);
            
            // validamos que el curso exista
            Asignacion asignacion = asignacionRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
            
            // traemos solo a los inscritos
            List<Alumno> alumnosInscritos = asignacion.getAlumnos();

            // buscamos si ya pase lista antes ese dia para recuperar los datos
            List<Asistencia> asistenciasGuardadas = asistenciaRepo.findByAsignacionIdAndFecha(id, localDate);

            List<Map<String, Object>> respuesta = new ArrayList<>();

            for (Alumno alumno : alumnosInscritos) {
                Map<String, Object> item = new HashMap<>();
                item.put("id", alumno.getId());
                item.put("nombre", alumno.getNombre());
                item.put("apellido", alumno.getApellido());
                item.put("matricula", alumno.getMatricula());

                // checar si este alumno ya tiene falta o asistencia guardada
                Optional<Asistencia> asistenciaExistente = asistenciasGuardadas.stream()
                        .filter(a -> a.getAlumno().getId().equals(alumno.getId()))
                        .findFirst();

                // si existe ponemos el estatus, si no 'presente' por defecto
                item.put("status", asistenciaExistente.map(Asistencia::getStatus).orElse("presente"));
                
                respuesta.add(item);
            }
            
            // ordenar por apellido para facilitar el pase de lista
            respuesta.sort((a, b) -> ((String) a.get("apellido")).compareTo((String) b.get("apellido")));

            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al cargar lista: " + e.getMessage()));
        }
    }

    // guardar o actualizar la lista
    @PostMapping("/asistencia")
    public ResponseEntity<?> guardarAsistencia(@RequestBody Map<String, Object> payload) {
        try {
            Long asignacionId = Long.valueOf(payload.get("asignacionId").toString());
            String fechaStr = payload.get("fecha").toString();
            LocalDate fecha = parsearFechaSegura(fechaStr);
            
            // lista de alumnos con sus estatus (presente, ausente, etc)
            List<Map<String, Object>> listaAlumnos = (List<Map<String, Object>>) payload.get("asistencias");

            Asignacion asignacion = asignacionRepo.findById(asignacionId).orElseThrow();

            for (Map<String, Object> item : listaAlumnos) {
                Long alumnoId = Long.valueOf(item.get("alumnoId").toString());
                String status = (String) item.get("status");

                Alumno alumno = alumnoRepo.findById(alumnoId).orElseThrow();

                // logica de upsert: buscamos si ya existe para no duplicar filas
                Asistencia asistencia = asistenciaRepo.findByAsignacionIdAndAlumnoIdAndFecha(asignacionId, alumnoId, fecha)
                        .orElse(new Asistencia()); 

                // si es nuevo llenamos las relaciones
                if (asistencia.getId() == null) {
                    asistencia.setAsignacion(asignacion);
                    asistencia.setAlumno(alumno);
                    asistencia.setFecha(fecha);
                }
                
                asistencia.setStatus(status); 
                asistenciaRepo.save(asistencia);
            }

            return ResponseEntity.ok(Map.of("message", "Asistencia guardada correctamente"));
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.badRequest().body(Map.of("error", "Error backend: " + e.getMessage()));
        }
    }

    // generar el reporte mensual (la sabana)
    @GetMapping("/asignaciones/{id}/reporte-asistencia")
    public ResponseEntity<?> getReporteAsistencia(
            @PathVariable Long id, 
            @RequestParam String inicio, 
            @RequestParam String fin) {
        try {
            LocalDate fechaInicio = parsearFechaSegura(inicio);
            LocalDate fechaFin = parsearFechaSegura(fin);

            Asignacion asignacion = asignacionRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
            
            List<Alumno> alumnos = asignacion.getAlumnos();
            
            // traemos todos los registros de jalón para no hacer mil consultas a la bd
            List<Asistencia> registros = asistenciaRepo.findByAsignacionIdAndFechaBetweenOrderByFechaAsc(id, fechaInicio, fechaFin);

            // armamos la estructura para la tabla
            List<Map<String, Object>> reporte = new ArrayList<>();

            for (Alumno alumno : alumnos) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("id", alumno.getId());
                fila.put("nombre", alumno.getNombre());
                fila.put("apellido", alumno.getApellido());
                fila.put("matricula", alumno.getMatricula());

                // mapa de fecha -> estatus
                Map<String, String> asistenciaPorDia = new HashMap<>();
                
                int totalPresentes = 0;
                int totalAusentes = 0;
                int totalRetardos = 0;

                // filtramos en memoria los registros de este alumno
                for (Asistencia a : registros) {
                    if (a.getAlumno().getId().equals(alumno.getId())) {
                        asistenciaPorDia.put(a.getFecha().toString(), a.getStatus());
                        
                        // contadores
                        if(a.getStatus().equals("presente")) totalPresentes++;
                        else if(a.getStatus().equals("ausente")) totalAusentes++;
                        else if(a.getStatus().equals("retardo")) totalRetardos++;
                    }
                }
                
                fila.put("asistencias", asistenciaPorDia);
                fila.put("totalPresentes", totalPresentes);
                fila.put("totalAusentes", totalAusentes);
                fila.put("totalRetardos", totalRetardos);

                reporte.add(fila);
            }
            
            // en orden alfabetico
            reporte.sort((a, b) -> ((String) a.get("apellido")).compareTo((String) b.get("apellido")));

            return ResponseEntity.ok(reporte);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error generando reporte: " + e.getMessage()));
        }
    }
}