package com.example.RedSismica.DTO;

import java.time.LocalDateTime;
import java.util.List;

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

    private Long eventoId;
    private Long estacionId;

    private List<MuestraSismicaDTO> muestrasSismicas;

    private LocalDateTime fechaHoraRegistroMuestra;
    private LocalDateTime fechaHoraRegristo;
    private Double frecuenciaMuestreo;
    private String condicionAlarma;
}