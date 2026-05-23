package com.clinicasocorro.controller;

import com.clinicasocorro.dto.RegistroDTO;
import com.clinicasocorro.repository.PersonaRepository;
import com.clinicasocorro.repository.UsuarioRepository;
import com.clinicasocorro.service.RegistroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistroController {

    @Autowired
    private RegistroService registroService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("registroDTO", new RegistroDTO());
        return "registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@Valid @ModelAttribute("registroDTO") RegistroDTO registroDTO,
                                   BindingResult result,
                                   Model model) {
        
        // Validaciones personalizadas de duplicados en BD antes de intentar persistir
        if (registroDTO.getUsername() != null && !registroDTO.getUsername().isBlank()) {
            if (usuarioRepository.findByUsername(registroDTO.getUsername()) != null) {
                result.rejectValue("username", "error.username", "El nombre de usuario ya está registrado");
            }
        }

        if (registroDTO.getDni() != null && !registroDTO.getDni().isBlank()) {
            if (personaRepository.findByDni(registroDTO.getDni()).isPresent()) {
                result.rejectValue("dni", "error.dni", "El DNI ya está registrado");
            }
        }

        if (registroDTO.getCorreo() != null && !registroDTO.getCorreo().isBlank()) {
            if (personaRepository.findByCorreo(registroDTO.getCorreo()).isPresent()) {
                result.rejectValue("correo", "error.correo", "El correo electrónico ya está registrado");
            }
        }

        if (result.hasErrors()) {
            return "registro";
        }

        try {
            registroService.registrarPaciente(registroDTO);
            return "redirect:/login?registrado=true";
        } catch (Exception e) {
            model.addAttribute("errorGeneral", "Ocurrió un error inesperado al procesar el registro: " + e.getMessage());
            return "registro";
        }
    }
}
