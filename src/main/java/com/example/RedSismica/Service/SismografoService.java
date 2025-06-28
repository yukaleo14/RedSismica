package com.example.RedSismica.Service;

import org.springframework.stereotype.Service;

import com.example.RedSismica.Model.EstacionSismologica;

@Service
public class SismografoService {

    public String esSismografo(EstacionSismologica estacion) {
        return estacion.getNombre() + ", " + estacion.getCodigoEstacion();
    }
    
}
