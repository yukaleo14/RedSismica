package com.example.RedSismica.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class CambioEstado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;

    @OneToOne
    private EstadoEvento estadoEvento;

    @ManyToOne
    @JoinColumn(name = "evento_sismico_id")
    private EventoSismico eventoSismico;

    private Boolean actual;

    public boolean esActual() {
       return this.estadoEvento.esActual();
    }
}
