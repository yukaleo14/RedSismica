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
public class EventoSismicoDTO {
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
    private Long estadoEventoId;
    private Long serieTemporalId;
    private Long clasificacionId;
}
