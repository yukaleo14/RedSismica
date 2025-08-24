package com.example.RedSismica.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RedSismica.Model.Clasificacion;
import com.example.RedSismica.Repository.ClasificacionRepository;

@Service
public class ClasificacionService {
    @Autowired 
    private ClasificacionRepository repo;

    public Clasificacion clasificar(Clasificacion clasificacion) {
        return repo.save(clasificacion);
    }

    // Metodo nro 39 --------------------------------------------------------------------------------------------------
    // Metodo que obtendra el nombre de la clasificacion del evento sismico seleccionado
    public String getNombre(Clasificacion clasificacion) {
        return clasificacion.getNombre();
    }
}
