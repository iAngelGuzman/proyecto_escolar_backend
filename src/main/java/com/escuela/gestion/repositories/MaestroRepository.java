package com.escuela.gestion.repositories;

import com.escuela.gestion.models.Maestro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MaestroRepository extends JpaRepository<Maestro, Long> {
    // Spring crea la consulta SQL automáticamente al ver "findByEmail"
    Optional<Maestro> findByEmail(String email); 
}