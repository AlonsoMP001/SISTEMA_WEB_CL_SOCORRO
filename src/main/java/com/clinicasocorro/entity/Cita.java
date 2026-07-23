package com.clinicasocorro.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Long idCita;

    @ManyToOne
    @JoinColumn(name = "id_paciente", referencedColumnName = "id_persona")
    private Persona paciente;

    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "id_consultorio")
    private Consultorio consultorio;

    @Column(name = "fecha_cita")
    private LocalDate fechaCita;

    @Column(name = "hora_cita")
    private LocalTime horaCita;

    @Column(name = "estado_cita", length = 50)
    private String estadoCita;

    @Column(name = "monto_pago")
    private BigDecimal montoPago;
}
