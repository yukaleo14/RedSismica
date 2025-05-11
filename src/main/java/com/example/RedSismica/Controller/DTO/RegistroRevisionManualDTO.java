package com.example.RedSismica.Controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroRevisionManualDTO {
    private Long eventoId;
    private String resultadoRevision;
    private String observacion;
    private Long revisorId; //Identificador de Revisor
}
