package com.clinicasocorro.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "diagnosticos_atencion")
@Data
public class DiagnosticoAtencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_det_diag")
    private Long idDetDiag;

    @ManyToOne
    @JoinColumn(name = "id_atencion")
    private AtencionMedica atencionMedica;

    @Column(name = "codigo_cie10", length = 10)
    private String codigoCie10;

    @Column(name = "descripcion_diagnostico", columnDefinition = "TEXT")
    private String descripcionDiagnostico;

    @Column(name = "tipo_diagnostico", length = 20)
    private String tipoDiagnostico;
}
