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
}
