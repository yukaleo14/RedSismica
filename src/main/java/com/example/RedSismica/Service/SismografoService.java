package com.example.RedSismica.Service;

import org.springframework.stereotype.Service;

import com.example.RedSismica.Model.EstacionSismologica;

@Service
public class SismografoService {

    // Metodo nro 47 --------------------------------------------------------------------------------------------------
    // Verifica el sismografo y busca obtener los datos de la estacion sismologica
    // asociada a la serie temporal del evento sismico seleccionado
    public String esSismografo(EstacionSismologica estacion) {
        return estacion.getNombre() + ", " + estacion.getCodigoEstacion();
    }
    
}
