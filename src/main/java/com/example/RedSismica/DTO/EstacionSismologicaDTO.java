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
public class EstacionSismologicaDTO {
    private Long codigoEstacion;
    private String nombre;
    private Double latitud;
    private Double longitud;
    private LocalDateTime fechaSolicitudCertificacion;
    private String nroCerificacionAdquisicion;
    
    public void setId(Object id) {
        throw new UnsupportedOperationException("Unimplemented method 'setId'");
    }
    public Object getId() {
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }
}