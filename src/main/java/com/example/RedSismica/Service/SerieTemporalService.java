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

    @Autowired MuestraSismicaService muestraSismicaService;

    @Autowired
    TipoDeDatoService tipoDeDatoService;

    @Autowired
    private SismografoService sismografoService;

    public List<SerieTemporal> obtenerSeries(EventoSismico evento) {
        return repo.findByEvento(evento);
    }
    public void getMuestras() {
       muestraSismicaService.getDatos(tipoDeDatoService);
    }


    public SerieTemporal getById(Long idSerie) {
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    public String obtenerEstacionesSismologicas(SerieTemporal serie) {
        return sismografoService.esSismografo(serie.getEstacionSismologica());
    }
    
    //listar estacion sismologica en las que pertenece la serie temporal
    public List<SerieTemporal> listarEstacionesSismologicas(SerieTemporal serie) {
        return repo.findByEstacionSismologica(serie.getEstacionSismologica());
    }
}
