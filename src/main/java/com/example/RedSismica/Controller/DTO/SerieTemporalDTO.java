package com.example.RedSismica.Controller.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SerieTemporalDTO {
    private Long estacionSismologicaId;
    private String estacionSismologicaCodigo;
    private LocalDateTime instanteTiempo;
    private Double valorVelocidadOnda;
    private Double valorFrecuenciaOnda;
    private Double valorLongitudOnda;
}
