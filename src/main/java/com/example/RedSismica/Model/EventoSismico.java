package com.example.RedSismica.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    //Getter
   
    public void esPendRevision() {
        this.estadoEvento.esPendRevision();
    }

    public void esAutoDetectado() {
        this.estadoEvento.esAutoDetectado();
    }


}


