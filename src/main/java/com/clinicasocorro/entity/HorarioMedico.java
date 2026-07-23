package com.clinicasocorro.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Data
@Entity
@Table(name = "horarios_medicos")
public class HorarioMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario")
    private Long idHorario;

    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "id_sede")
    private Sede sede;

    @Column(name = "dia_semana")
    private Integer diaSemana; // 1 = Lunes, etc.

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_fin")
    private LocalTime horaFin;
}
