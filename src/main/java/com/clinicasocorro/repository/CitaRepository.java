package com.clinicasocorro.repository;

import com.clinicasocorro.entity.Cita;
import com.clinicasocorro.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByMedicoOrderByFechaCitaAscHoraCitaAsc(Medico medico);
    
    List<Cita> findByPacienteOrderByFechaCitaDescHoraCitaDesc(com.clinicasocorro.entity.Persona paciente);
    
    boolean existsByMedicoAndFechaCitaAndHoraCitaAndEstadoCitaNot(Medico medico, java.time.LocalDate fechaCita, java.time.LocalTime horaCita, String estadoCita);
}
