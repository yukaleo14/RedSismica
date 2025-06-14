package com.example.RedSismica.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RedSismica.Model.EventoSismico;
import com.example.RedSismica.Model.SerieTemporal;
import com.example.RedSismica.Repository.SerieTemporalRepository;

@Service
public class SerieTemporalService {
    @Autowired 
    private SerieTemporalRepository repo;

    public List<SerieTemporal> obtenerSeries(EventoSismico evento) {
        return repo.findByEvento(evento);
    }

    public SerieTemporal getById(Long idSerie) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }
}
