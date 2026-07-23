package com.clinicasocorro.controller;

import com.clinicasocorro.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class probando {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String inicio() {
        return "index";
    }

    @GetMapping("/staff")
    public String verStaff(Model model) {
        // Llamamos al servicio para obtener la lista de médicos activos
        // y la agregamos al modelo de Thymeleaf con el nombre "medicos"
        model.addAttribute("medicos", usuarioService.listarStaffMedicoActivo());
        return "staff-medico"; 
    }



    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @GetMapping("/login-success")
    public String exito() {
        return "login-success";
    }
}