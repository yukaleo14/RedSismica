package com.example.RedSismica.DTO;

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
    private Long id;
    private EventoSismicoDTO evento;
    private EstacionSismologicaDTO estacionSismologica;
    private MuestraSismicaDTO muestraSismica;
    private LocalDateTime fechaHoraRegistroMuestra;
    private LocalDateTime fechaHoraRegristo;
    private Double frecuenciaMuestreo;
    private String condicionAlarma;
}