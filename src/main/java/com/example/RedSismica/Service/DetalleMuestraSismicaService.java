package com.example.RedSismica.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RedSismica.Model.DetalleMuestraSismica;
import com.example.RedSismica.Model.MuestraSismica;
import com.example.RedSismica.Repository.DetalleMuestraSismicaRepository;

@Service
public class DetalleMuestraSismicaService {
    @Autowired 
    private DetalleMuestraSismicaRepository repo;

    public List<DetalleMuestraSismica> obtenerDetalles(MuestraSismica muestra) {
        return repo.findByMuestraSismica(muestra);
    }
}