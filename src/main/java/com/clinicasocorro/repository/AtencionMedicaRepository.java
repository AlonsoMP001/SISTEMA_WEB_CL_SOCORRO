package com.clinicasocorro.repository;

import com.clinicasocorro.entity.AtencionMedica;
import com.clinicasocorro.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtencionMedicaRepository extends JpaRepository<AtencionMedica, Long> {
    List<AtencionMedica> findByMedicoOrderByFechaAtencionDesc(Medico medico);
    
    List<AtencionMedica> findByHistoriaClinicaOrderByFechaAtencionDesc(com.clinicasocorro.entity.HistoriaClinica hc);
}
