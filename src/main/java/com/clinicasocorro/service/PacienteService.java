package com.clinicasocorro.service;

import com.clinicasocorro.entity.AtencionMedica;
import com.clinicasocorro.entity.Cita;
import com.clinicasocorro.entity.HistoriaClinica;
import com.clinicasocorro.entity.Medico;
import com.clinicasocorro.entity.Persona;
import com.clinicasocorro.entity.Usuario;
import com.clinicasocorro.repository.AtencionMedicaRepository;
import com.clinicasocorro.repository.CitaRepository;
import com.clinicasocorro.repository.HistoriaClinicaRepository;
import com.clinicasocorro.repository.MedicoRepository;
import com.clinicasocorro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private AtencionMedicaRepository atencionMedicaRepository;

    @Autowired
    private HistoriaClinicaRepository historiaClinicaRepository;

    public Persona getPacienteLogueado(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario != null && usuario.getPersona() != null) {
            return usuario.getPersona();
        }
        return null;
    }

    public List<Medico> getMedicosDisponibles() {
        return medicoRepository.findAll();
    }

    public List<AtencionMedica> getHistorialMedico(Persona paciente) {
        Optional<HistoriaClinica> hcOpt = historiaClinicaRepository.findByPaciente(paciente);
        if (hcOpt.isPresent()) {
            return atencionMedicaRepository.findByHistoriaClinicaOrderByFechaAtencionDesc(hcOpt.get());
        }
        return new ArrayList<>();
    }

    public List<Cita> getCitasByPaciente(Persona paciente) {
        return citaRepository.findByPacienteOrderByFechaCitaDescHoraCitaDesc(paciente);
    }

    @Transactional
    public void agendarCita(Cita cita) throws Exception {
        // Verificar si el médico ya tiene una cita agendada para esa fecha y hora
        boolean cruceMedico = citaRepository.existsByMedicoAndFechaCitaAndHoraCitaAndEstadoCitaNot(
                cita.getMedico(),
                cita.getFechaCita(),
                cita.getHoraCita(),
                "CANCELADA"
        );

        if (cruceMedico) {
            throw new Exception("El médico ya tiene una cita agendada en ese horario.");
        }

        // Si no hay cruces, se guarda la cita con estado "PROGRAMADA"
        cita.setEstadoCita("PROGRAMADA");
        citaRepository.save(cita);
    }

    @Transactional
    public void cancelarCita(Long idCita, Persona pacienteLogueado) throws Exception {
        Cita cita = citaRepository.findById(idCita)
                .orElseThrow(() -> new Exception("Cita no encontrada."));

        if (!cita.getPaciente().getIdPersona().equals(pacienteLogueado.getIdPersona())) {
            throw new Exception("No tiene permisos para cancelar esta cita.");
        }

        if ("ATENDIDA".equals(cita.getEstadoCita())) {
            throw new Exception("No se puede cancelar una cita que ya fue atendida.");
        }

        cita.setEstadoCita("CANCELADA");
        citaRepository.save(cita);
    }
}
