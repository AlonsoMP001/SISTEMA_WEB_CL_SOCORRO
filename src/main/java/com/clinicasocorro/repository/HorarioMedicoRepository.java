package com.clinicasocorro.repository;

import com.clinicasocorro.entity.HorarioMedico;
import com.clinicasocorro.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorarioMedicoRepository extends JpaRepository<HorarioMedico, Long> {
    List<HorarioMedico> findByMedicoOrderByDiaSemanaAscHoraInicioAsc(Medico medico);
}
