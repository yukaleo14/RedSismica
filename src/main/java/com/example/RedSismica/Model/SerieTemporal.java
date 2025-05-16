package com.example.RedSismica.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class SerieTemporal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private EventoSismico evento;

    @ManyToOne
    @JoinColumn(name = "estacion_id")
    private EstacionSismologica estacionSismologica;

    private LocalDateTime instanteTiempo;
    private Double valorVelocidadOnda;
    private Double valorFrecuenciaOnda;
    private Double valorLongitudOnda;
}
