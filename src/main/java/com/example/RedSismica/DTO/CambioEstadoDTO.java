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
public class CambioEstadoDTO {
    private Long id;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private Long eventoSismicoId;
    public void setEstadoEventoId(Long id2) {
        throw new UnsupportedOperationException("Unimplemented method 'setEstadoEventoId'");
    }
   
}
