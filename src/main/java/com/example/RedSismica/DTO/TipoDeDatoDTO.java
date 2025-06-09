package com.example.RedSismica.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoDeDatoDTO {
    private Long id;
    private String denominacion;
    private String nombreUnidadDeMedida;
    private Double valorUmbral;

}