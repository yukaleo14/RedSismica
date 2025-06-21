package com.example.RedSismica.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstacionSismologica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoEstacion;
    private String nombre;
    private Double latitud;
    private Double longitud;
    private LocalDateTime fechaSolicitudCertificacion;
    private String nroCerificacionAdquisicion;

    @ManyToOne
    @JoinColumn(name = "evento_sismico_id")
    private EventoSismico eventoSismico;

    public Long getCodigoEstacion() {
        return codigoEstacion;
    }

    public String getNombre() {
        return nombre;
    }

    public Object getId() {
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

    public void setId(Object id) {
        throw new UnsupportedOperationException("Unimplemented method 'setId'");
    }
}
