package com.clinicasocorro.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "atenciones_medicas")
@Data
public class AtencionMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atencion")
    private Long idAtencion;

    @ManyToOne
    @JoinColumn(name = "id_hc")
    private HistoriaClinica historiaClinica;

    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;

    @OneToOne
    @JoinColumn(name = "id_cita", unique = true)
    private Cita cita;

    @Column(name = "fecha_atencion")
    private LocalDateTime fechaAtencion;

    @Column(name = "motivo_consulta", columnDefinition = "TEXT")
    private String motivoConsulta;

    @Column(name = "triaje_presion", length = 10)
    private String triajePresion;

    @Column(name = "triaje_temperatura", precision = 4, scale = 2)
    private BigDecimal triajeTemperatura;

    @Column(name = "triaje_peso", precision = 5, scale = 2)
    private BigDecimal triajePeso;

    @Column(name = "triaje_saturacion")
    private Long triajeSaturacion;

    @Column(name = "triaje_frecuencia_cardiaca")
    private Long triajeFrecuenciaCardiaca;

    @OneToMany(mappedBy = "atencionMedica", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<DiagnosticoAtencion> diagnosticos;

    @PrePersist
    protected void onCreate() {
        if(this.fechaAtencion == null) {
            this.fechaAtencion = LocalDateTime.now();
        }
    }
}
