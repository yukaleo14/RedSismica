package com.example.RedSismica.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RedSismica.Model.MuestraSismica;
import com.example.RedSismica.Model.SerieTemporal;
import com.example.RedSismica.Repository.MuestraSismicaRepository;

@Service
public class MuestraSismicaService {
    @Autowired 
    private MuestraSismicaRepository repo;

    public List<MuestraSismica> obtenerMuestras(SerieTemporal serie) {
        return repo.findBySerieTemporal(serie);
    }
}
