package com.clinicasocorro.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "historias_clinicas")
@Data
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hc")
    private Long idHc;

    @OneToOne
    @JoinColumn(name = "id_paciente", unique = true)
    private Persona paciente;

    @Column(name = "nro_historia", length = 20, unique = true)
    private String nroHistoria;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        if(this.fechaCreacion == null) {
            this.fechaCreacion = LocalDateTime.now();
        }
    }
}
