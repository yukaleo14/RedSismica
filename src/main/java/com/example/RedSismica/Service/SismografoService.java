package com.example.RedSismica.Service;

import com.example.RedSismica.Model.EstacionSismologica;

public class SismografoService {

    public String esSismografo(EstacionSismologica estacion) {
        return estacion.getNombre() + ", " + estacion.getCodigoEstacion();
    }
    
}
