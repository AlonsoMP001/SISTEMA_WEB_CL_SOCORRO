package com.clinicasocorro.service;

import com.clinicasocorro.entity.Cita;
import com.clinicasocorro.entity.HorarioMedico;
import com.clinicasocorro.entity.Medico;
import com.clinicasocorro.entity.Usuario;
import com.clinicasocorro.repository.CitaRepository;
import com.clinicasocorro.repository.HorarioMedicoRepository;
import com.clinicasocorro.repository.MedicoRepository;
import com.clinicasocorro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private HorarioMedicoRepository horarioMedicoRepository;

    public Medico getMedicoLogueado(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario != null && usuario.getPersona() != null) {
            return medicoRepository.findByPersona(usuario.getPersona()).orElse(null);
        }
        return null;
    }

    public List<Cita> getCitasByMedico(Medico medico) {
        return citaRepository.findByMedicoOrderByFechaCitaAscHoraCitaAsc(medico);
    }

    public List<HorarioMedico> getHorariosByMedico(Medico medico) {
        return horarioMedicoRepository.findByMedicoOrderByDiaSemanaAscHoraInicioAsc(medico);
    }

    @Autowired
    private com.clinicasocorro.repository.HistoriaClinicaRepository historiaClinicaRepository;
    
    @Autowired
    private com.clinicasocorro.repository.AtencionMedicaRepository atencionMedicaRepository;
    
    @Autowired
    private com.clinicasocorro.repository.DiagnosticoAtencionRepository diagnosticoAtencionRepository;

    @org.springframework.transaction.annotation.Transactional
    public void atenderCita(Long idCita, com.clinicasocorro.entity.AtencionMedica atencion, com.clinicasocorro.entity.DiagnosticoAtencion diagnostico) {
        Cita cita = citaRepository.findById(idCita).orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        
        // 1. Obtener o crear Historia Clínica para el paciente
        com.clinicasocorro.entity.HistoriaClinica hc = historiaClinicaRepository.findByPaciente(cita.getPaciente())
            .orElseGet(() -> {
                com.clinicasocorro.entity.HistoriaClinica nuevaHc = new com.clinicasocorro.entity.HistoriaClinica();
                nuevaHc.setPaciente(cita.getPaciente());
                // Generar número de historia simple
                nuevaHc.setNroHistoria("HC-" + cita.getPaciente().getDni());
                return historiaClinicaRepository.save(nuevaHc);
            });
            
        // 2. Guardar Atención Médica
        atencion.setCita(cita);
        atencion.setMedico(cita.getMedico());
        atencion.setHistoriaClinica(hc);
        atencion.setFechaAtencion(java.time.LocalDateTime.now());
        com.clinicasocorro.entity.AtencionMedica savedAtencion = atencionMedicaRepository.save(atencion);
        
        // 3. Guardar Diagnóstico
        diagnostico.setAtencionMedica(savedAtencion);
        diagnosticoAtencionRepository.save(diagnostico);
        
        // 4. Actualizar estado de la Cita
        cita.setEstadoCita("ATENDIDA");
        citaRepository.save(cita);
    }

    public List<com.clinicasocorro.entity.AtencionMedica> getHistorialAtenciones(Medico medico) {
        return atencionMedicaRepository.findByMedicoOrderByFechaAtencionDesc(medico);
    }
}
