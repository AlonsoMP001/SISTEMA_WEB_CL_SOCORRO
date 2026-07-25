package com.clinicasocorro.controller;

import com.clinicasocorro.entity.Medico;
import com.clinicasocorro.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/medico")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    private Medico getAuthenticatedMedico() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String username = auth.getName();
            return medicoService.getMedicoLogueado(username);
        }
        return null;
    }

    @GetMapping("/perfil")
    public String perfil(Model model) {
        Medico medico = getAuthenticatedMedico();
        if (medico == null) {
            return "redirect:/login";
        }
        model.addAttribute("medico", medico);
        return "medico-perfil";
    }

    @GetMapping("/citas")
    public String citas(Model model) {
        Medico medico = getAuthenticatedMedico();
        if (medico == null) {
            return "redirect:/login";
        }
        model.addAttribute("medico", medico);
        model.addAttribute("citas", medicoService.getCitasByMedico(medico));
        return "medico-citas";
    }

    @GetMapping("/horarios")
    public String horarios(Model model) {
        Medico medico = getAuthenticatedMedico();
        if (medico == null) {
            return "redirect:/login";
        }
        model.addAttribute("medico", medico);
        model.addAttribute("horarios", medicoService.getHorariosByMedico(medico));
        return "medico-horarios";
    }

    @GetMapping("/historial")
    public String historial(Model model) {
        Medico medico = getAuthenticatedMedico();
        if (medico == null) {
            return "redirect:/login";
        }
        model.addAttribute("medico", medico);
        model.addAttribute("atenciones", medicoService.getHistorialAtenciones(medico));
        return "medico-historial";
    }

    @org.springframework.web.bind.annotation.PostMapping("/citas/atender")
    @org.springframework.web.bind.annotation.ResponseBody
    public org.springframework.http.ResponseEntity<?> atenderCita(
            @org.springframework.web.bind.annotation.RequestParam("idCita") Long idCita,
            @org.springframework.web.bind.annotation.RequestParam("motivoConsulta") String motivoConsulta,
            @org.springframework.web.bind.annotation.RequestParam("triajePresion") String triajePresion,
            @org.springframework.web.bind.annotation.RequestParam("triajeTemperatura") java.math.BigDecimal triajeTemperatura,
            @org.springframework.web.bind.annotation.RequestParam("triajePeso") java.math.BigDecimal triajePeso,
            @org.springframework.web.bind.annotation.RequestParam("triajeSaturacion") Long triajeSaturacion,
            @org.springframework.web.bind.annotation.RequestParam("triajeFrecuenciaCardiaca") Long triajeFrecuenciaCardiaca,
            @org.springframework.web.bind.annotation.RequestParam("codigoCie10") String codigoCie10,
            @org.springframework.web.bind.annotation.RequestParam("descripcionDiagnostico") String descripcionDiagnostico) {
            
        try {
            com.clinicasocorro.entity.AtencionMedica atencion = new com.clinicasocorro.entity.AtencionMedica();
            atencion.setMotivoConsulta(motivoConsulta);
            atencion.setTriajePresion(triajePresion);
            atencion.setTriajeTemperatura(triajeTemperatura);
            atencion.setTriajePeso(triajePeso);
            atencion.setTriajeSaturacion(triajeSaturacion);
            atencion.setTriajeFrecuenciaCardiaca(triajeFrecuenciaCardiaca);
            
            com.clinicasocorro.entity.DiagnosticoAtencion diagnostico = new com.clinicasocorro.entity.DiagnosticoAtencion();
            diagnostico.setCodigoCie10(codigoCie10);
            diagnostico.setDescripcionDiagnostico(descripcionDiagnostico);
            diagnostico.setTipoDiagnostico("DEFINITIVO"); // default
            
            medicoService.atenderCita(idCita, atencion, diagnostico);
            
            return org.springframework.http.ResponseEntity.ok().body("{\"success\":true}");
        } catch (Exception e) {
            e.printStackTrace();
            return org.springframework.http.ResponseEntity.badRequest().body("{\"success\":false, \"message\":\"" + e.getMessage() + "\"}");
        }
    }
}
