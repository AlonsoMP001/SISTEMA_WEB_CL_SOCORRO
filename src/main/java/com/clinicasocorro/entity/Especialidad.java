package com.clinicasocorro.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "especialidades")
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_especialidad")
    private Long idEspecialidad;

    @Column(name = "nombre_especialidad", length = 100) // Revisa si en tu BD es 'nombre_especialidad' o
                                                        // 'nombre_specialidad' como en tu script
    private String nombreEspecialidad;
}