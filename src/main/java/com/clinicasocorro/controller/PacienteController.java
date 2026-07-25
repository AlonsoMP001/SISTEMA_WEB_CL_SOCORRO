package com.clinicasocorro.controller;

import com.clinicasocorro.entity.Cita;
import com.clinicasocorro.entity.Consultorio;
import com.clinicasocorro.entity.Medico;
import com.clinicasocorro.entity.Persona;
import com.clinicasocorro.repository.ConsultorioRepository;
import com.clinicasocorro.repository.MedicoRepository;
import com.clinicasocorro.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
@Controller
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ConsultorioRepository consultorioRepository;

    private Persona getLoggedPaciente() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return pacienteService.getPacienteLogueado(auth.getName());
    }

    @GetMapping("/index")
    public String index(Model model) {
        Persona paciente = getLoggedPaciente();
        if (paciente == null)
            return "redirect:/login";

        model.addAttribute("paciente", paciente);
        return "paciente-index";
    }

    @GetMapping("/medicos")
    public String medicosDisponibles(Model model) {
        Persona paciente = getLoggedPaciente();
        if (paciente == null)
            return "redirect:/login";

        model.addAttribute("medicos", pacienteService.getMedicosDisponibles());
        return "paciente-medicos";
    }

    @GetMapping("/historial")
    public String historialMedico(Model model) {
        Persona paciente = getLoggedPaciente();
        if (paciente == null)
            return "redirect:/login";

        model.addAttribute("historial", pacienteService.getHistorialMedico(paciente));
        return "paciente-historial";
    }

    @GetMapping("/citas")
    public String citas(Model model) {
        Persona paciente = getLoggedPaciente();
        if (paciente == null)
            return "redirect:/login";

        model.addAttribute("citas", pacienteService.getCitasByPaciente(paciente));
        model.addAttribute("medicos", pacienteService.getMedicosDisponibles());
        model.addAttribute("consultorios", consultorioRepository.findAll());
        return "paciente-citas";
    }

    @PostMapping("/citas/agendar")
    public String agendarCita(
            @RequestParam("medicoId") Long medicoId,
            @RequestParam("consultorioId") Long consultorioId,
            @RequestParam("fechaCita") String fechaCitaStr,
            @RequestParam("horaCita") String horaCitaStr,
            RedirectAttributes redirectAttributes) {

        Persona paciente = getLoggedPaciente();
        if (paciente == null)
            return "redirect:/login";

        try {
            Medico medico = medicoRepository.findById(medicoId)
                    .orElseThrow(() -> new Exception("Médico no encontrado"));
            Consultorio consultorio = consultorioRepository.findById(consultorioId)
                    .orElseThrow(() -> new Exception("Consultorio no encontrado"));
            LocalDate fechaCita = LocalDate.parse(fechaCitaStr);
            LocalTime horaCita = LocalTime.parse(horaCitaStr);

            Cita cita = new Cita();
            cita.setPaciente(paciente);
            cita.setMedico(medico);
            cita.setConsultorio(consultorio);
            cita.setFechaCita(fechaCita);
            cita.setHoraCita(horaCita);

            pacienteService.agendarCita(cita);
            redirectAttributes.addFlashAttribute("mensajeExito", "Cita agendada exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al agendar: " + e.getMessage());
        }

        return "redirect:/paciente/citas";
    }

    @PostMapping("/citas/cancelar")
    public String cancelarCita(@RequestParam("idCita") Long idCita, RedirectAttributes redirectAttributes) {
        Persona paciente = getLoggedPaciente();
        if (paciente == null)
            return "redirect:/login";

        try {
            pacienteService.cancelarCita(idCita, paciente);
            redirectAttributes.addFlashAttribute("mensajeExito", "Cita cancelada exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al cancelar: " + e.getMessage());
        }

        return "redirect:/paciente/citas";
    }
}
