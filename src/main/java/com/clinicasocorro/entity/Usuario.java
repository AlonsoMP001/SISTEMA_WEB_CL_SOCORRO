package com.clinicasocorro.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "username", length = 50, unique = true)
    private String username;

    @Column(name = "password", length = 255)
    private String password;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;

    @Column(name = "estado")
    private Integer estado; // 1 para activo, 0 para inactivo

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "usuario")
    private Persona persona;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ========================================================
    // MÉTODOS DE SPRING SECURITY (UserDetails)
    // ========================================================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna el rol del usuario (el string en BD ya tiene el prefijo ROLE_)
        return Collections.singleton(new SimpleGrantedAuthority(rol.getNombreRol()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // La cuenta no expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // La cuenta no está bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Las credenciales no expiran
    }

    @Override
    public boolean isEnabled() {
        // El usuario solo puede entrar si el estado es 1 (Activo)
        return this.estado != null && this.estado == 1;
    }
}