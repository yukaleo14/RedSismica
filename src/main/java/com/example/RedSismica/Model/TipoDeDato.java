package com.example.RedSismica.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class TipoDeDato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String denominacion;
    private String nombreUnidadDeMedida;
    private Double valorUmbral;

    public String esTuDenominacion(String denominacion) {
        if (this.denominacion.equals(denominacion)) {
            return "La denominación es correcta";
        } else {
            return "La denominación no es correcta";
        }
    }

    public String getDenominacion() {
        return denominacion;
    }
}
