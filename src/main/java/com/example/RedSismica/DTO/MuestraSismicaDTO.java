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
public class MuestraSismicaDTO {
    private Long id;
    private LocalDateTime fechaHoraMuestra;
    private float velocidad;
    private float frecuencia;
    private float longitud;
    private DetalleMuestraSismicaDTO detalleMuestraSismica;;
}
