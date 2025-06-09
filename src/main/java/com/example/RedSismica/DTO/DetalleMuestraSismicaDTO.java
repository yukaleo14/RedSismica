package com.example.RedSismica.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleMuestraSismicaDTO {
    private Long id;
    private String valor;
    private TipoDeDatoDTO tipoDeDato;
}
