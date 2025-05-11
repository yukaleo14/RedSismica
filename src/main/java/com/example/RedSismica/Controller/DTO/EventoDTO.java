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
public class EventoDTO {
    private Long id;
    private LocalDateTime fechaHoraOrigen;
    private Double latitud;
    private Double longitud;
    private Double profundidad;
    private Double magnitud;
    private String alcance;
    private String origenGeneracion;
    private String estadoEvento;
}
