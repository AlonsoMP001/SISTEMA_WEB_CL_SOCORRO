package com.clinicasocorro.repository;

import com.clinicasocorro.entity.AtencionMedica;
import com.clinicasocorro.entity.DiagnosticoAtencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosticoAtencionRepository extends JpaRepository<DiagnosticoAtencion, Long> {
    List<DiagnosticoAtencion> findByAtencionMedica(AtencionMedica atencionMedica);
}
