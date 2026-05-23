package com.clinicasocorro.config;

import com.clinicasocorro.entity.Usuario;
import com.clinicasocorro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PasswordMigrationRunner implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        try {
            List<Usuario> usuarios = usuarioRepository.findAll();
            boolean migrated = false;
            for (Usuario usuario : usuarios) {
                String password = usuario.getPassword();
                // Si la contraseña no está vacía y no empieza con los prefijos típicos de BCrypt ($2a$, $2b$, $2y$)
                if (password != null && !password.startsWith("$2a$") && !password.startsWith("$2b$") && !password.startsWith("$2y$")) {
                    String encryptedPassword = passwordEncoder.encode(password);
                    usuario.setPassword(encryptedPassword);
                    usuarioRepository.save(usuario);
                    System.out.println("[MIGRACIÓN] Contraseña del usuario '" + usuario.getUsername() + "' migrada exitosamente a BCrypt.");
                    migrated = true;
                }
            }
            if (migrated) {
                System.out.println("[MIGRACIÓN] Todas las contraseñas antiguas han sido encriptadas con BCrypt.");
            }
        } catch (Exception e) {
            System.err.println("[MIGRACIÓN] Error al migrar las contraseñas: " + e.getMessage());
        }
    }
}
