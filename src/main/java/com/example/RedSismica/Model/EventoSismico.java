package com.example.RedSismica.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventoSismico {
    
    public static final String esAutoDetectado = null;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime fechaHoraOcurrencia;
    private LocalDateTime fechaHoraFin;
    private Double latitudHipocentro;
    private Double latitudEpicentro;
    private Double longitudEpicentro;
    private Double longitudHipocentro;
    private Double magnitud;
    private String alcance;
    private String origenGeneracion;
    private Boolean autoDetectado;
    private Boolean pendienteRevision;

    @ManyToOne
    private EstadoEvento estadoEvento;
    @ManyToOne
    private SerieTemporal serieTemporal;
    @OneToOne
    private Clasificacion clasificacion;

   
    public boolean esPendendienteRevision() {
        return this.estadoEvento.esPendienteRevision();
    }

    public boolean esAutoDetectado() {
        return this.estadoEvento.esAutoDetectado();
    }

    public void bloquear() {
        throw new UnsupportedOperationException("Unimplemented method 'bloquear'");
    }

    public boolean esPendienteRevision() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'esPendienteRevision'");
    }
 

}


