package com.clinicasocorro.repository;

import com.clinicasocorro.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findByDni(String dni);
    Optional<Persona> findByCorreo(String correo);
}
