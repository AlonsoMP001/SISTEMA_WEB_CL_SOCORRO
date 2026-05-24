package com.clinicasocorro.service;

import com.clinicasocorro.entity.Usuario;
import com.clinicasocorro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Obtiene la lista de usuarios que tienen el rol de MÉDICO
     * y cuyo estado sea "ACTIVO" (o el valor que uses en tu BD).
     */
    public List<Usuario> listarStaffMedicoActivo() {
        // El rol en BD se guarda como "ROLE_MEDICO" (con prefijo ROLE_)
        return usuarioRepository.findByRolAndEstado("ROLE_MEDICO", 1);
    }
}