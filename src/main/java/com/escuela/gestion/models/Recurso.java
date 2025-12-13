package com.escuela.gestion.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

// tabla para guardar material de apoyo (pdfs o links)
@Entity
@Table(name = "recursos")
public class Recurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String url;
    private String tipo; // valores: 'link' o 'file'

    // guardamos el archivo directo en base64 para no configurar almacenamientos externos usamos TEXT porque el string sale enorme
    @Column(name = "archivo_base64", columnDefinition = "TEXT")
    private String archivoBase64; 
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // materia a la que pertenece el recurso
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asignacion_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Asignacion asignacion;

    // getters y setters 
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getArchivoBase64() { return archivoBase64; }
    public void setArchivoBase64(String archivoBase64) { this.archivoBase64 = archivoBase64; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public Asignacion getAsignacion() { return asignacion; }
    public void setAsignacion(Asignacion asignacion) { this.asignacion = asignacion; }
}