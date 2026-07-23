package com.clinicasocorro.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "medicos")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medico")
    private Long idMedico;

    @OneToOne
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    private Persona persona;

    @Column(name = "cmp", unique = true)
    private String cmp;

    @ManyToOne
    @JoinColumn(name = "id_especialidad", referencedColumnName = "id_especialidad")
    private Especialidad especialidad;
}
