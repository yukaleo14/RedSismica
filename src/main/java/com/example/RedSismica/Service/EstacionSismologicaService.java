package com.example.RedSismica.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RedSismica.Model.EstacionSismologica;
import com.example.RedSismica.Model.EventoSismico;
import com.example.RedSismica.Repository.EstacionSismologicaRepository;

@Service
public class EstacionSismologicaService {
    @Autowired 
    private EstacionSismologicaRepository repo;

    public List<EstacionSismologica> obtenerEstaciones(EventoSismico evento) {
        return repo.findByEventoSismico(evento);
    }
}