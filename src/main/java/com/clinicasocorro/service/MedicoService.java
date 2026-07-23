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
}
