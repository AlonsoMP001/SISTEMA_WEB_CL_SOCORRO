package com.clinicasocorro.service;

import com.clinicasocorro.dto.RegistroDTO;
import com.clinicasocorro.entity.Persona;
import com.clinicasocorro.entity.Rol;
import com.clinicasocorro.entity.Usuario;
import com.clinicasocorro.repository.PersonaRepository;
import com.clinicasocorro.repository.RolRepository;
import com.clinicasocorro.repository.UsuarioRepository;
// ESTO ES LA CLAVE DE ENCRIPTACION 
import org.springframework.security.crypto.password.PasswordEncoder;
// FIN ESTO ES LA CLAVE DE ENCRIPTACION 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistroService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void registrarPaciente(RegistroDTO dto) {
        // 1. Obtener o crear el rol PACIENTE
        Rol rol = rolRepository.findByNombreRol("PACIENTE")
                .orElseGet(() -> {
                    Rol nuevoRol = new Rol();
                    nuevoRol.setNombreRol("PACIENTE");
                    return rolRepository.save(nuevoRol);
                });

        // 2. Crear y guardar el Usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword())); // Encriptar contraseña con BCrypt
        usuario.setRol(rol);
        usuario.setEstado(1); // 1 para Activo
        usuario = usuarioRepository.save(usuario);

        // 3. Crear y guardar la Persona vinculada al Usuario
        Persona persona = new Persona();
        persona.setDni(dto.getDni());
        persona.setNombres(dto.getNombres());
        persona.setApellidos(dto.getApellidos());
        persona.setFechaNacimiento(dto.getFechaNacimiento());
        persona.setGenero(dto.getGenero());
        persona.setTelefono(dto.getTelefono());
        persona.setCorreo(dto.getCorreo());
        persona.setDireccion(dto.getDireccion());
        persona.setUsuario(usuario);
        personaRepository.save(persona);
    }
}
