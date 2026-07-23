package com.clinicasocorro.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sedes")
public class Sede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sede")
    private Long idSede;

    @Column(name = "nombre_sede", length = 100)
    private String nombreSede;

    @Column(name = "direccion", length = 255)
    private String direccion;
}
