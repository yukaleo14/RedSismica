package com.example.RedSismica.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RedSismica.Model.Clasificacion;
import com.example.RedSismica.Repository.ClasificacionRepository;

@Service
public class ClasificacionService {
    @Autowired 
    private ClasificacionRepository repo;

    public Clasificacion clasificarInformacion(Clasificacion clasificacion) {
        return repo.save(clasificacion);
    }
}
