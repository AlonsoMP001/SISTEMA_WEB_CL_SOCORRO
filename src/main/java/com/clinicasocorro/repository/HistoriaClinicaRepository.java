package com.clinicasocorro.repository;

import com.clinicasocorro.entity.HistoriaClinica;
import com.clinicasocorro.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica, Long> {
    Optional<HistoriaClinica> findByPaciente(Persona paciente);
}
