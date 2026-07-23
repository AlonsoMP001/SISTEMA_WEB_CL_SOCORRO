package com.clinicasocorro.repository;

import com.clinicasocorro.entity.Medico;
import com.clinicasocorro.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findByPersona(Persona persona);
}
