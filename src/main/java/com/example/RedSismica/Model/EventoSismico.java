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

    @ManyToOne
    private EstadoEvento estadoEvento;
    @ManyToOne
    private SerieTemporal serieTemporal;
    @OneToOne
    private Clasificacion clasificacion;

    //Getter
    public Long getId() {
        return id;
    }
    public LocalDateTime getFechaHoraOcurrencia() {
        return fechaHoraOcurrencia;
    }
    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }
    public Double getLatitudHipocentro() {
        return latitudHipocentro;
    }
    public Double getLatitudEpicentro() {
        return latitudEpicentro;
    }
    public Double getLongitudEpicentro() {
        return longitudEpicentro;
    }
    public Double getLongitudHipocentro() {
        return longitudHipocentro;
    }
    public Double getMagnitud() {
        return magnitud;
    }
    public String getAlcance() {
        return alcance;
    }
    public String getOrigenGeneracion() {
        return origenGeneracion;
    }
    public EstadoEvento getEstadoEvento() {
        return estadoEvento;
    }

   
    public void esPendRevision() {
        this.estadoEvento.esPendRevision();
    }

    public void esAutoDetectado() {
        this.estadoEvento.esAutoDetectado();
    }
 

}


