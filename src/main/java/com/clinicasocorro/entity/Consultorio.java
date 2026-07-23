package com.clinicasocorro.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "consultorios")
public class Consultorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consultorio")
    private Long idConsultorio;

    @Column(name = "numero_consultorio", length = 20)
    private String numeroConsultorio;

    @ManyToOne
    @JoinColumn(name = "id_sede")
    private Sede sede;
}
