package com.example.RedSismica.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.RedSismica.Model.TipoDeDato;

public class TipoDeDatoService {
    @Autowired
    private TipoDeDato tipoDeDato;

     public String esTuDenominacion(String denominacion) {
        if (tipoDeDato.denominacion.equals(denominacion)) {
            return "La denominación es correcta";
        } else {
            return "La denominación no es correcta";
        }
    }

    public String getDenominacion() {
        return tipoDeDato.denominacion;
    }
    
}
