package com.example.RedSismica.Service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RedSismica.Model.CambioEstado;
import com.example.RedSismica.Model.EventoSismico;
import com.example.RedSismica.Repository.CambioEstadoRepository;

@Service
public class CambioEstadoService {
    @Autowired 
    private CambioEstadoRepository repo;

    public CambioEstado getCambioEstadoActual(EventoSismico evento) {
        return repo.findByEventoSismicoAndActualTrue(evento).orElse(null);
    }

    public void finalizarCambio(CambioEstado cambio) {
        cambio.setFechaHoraFin(LocalDateTime.now());
        repo.save(cambio);
    }

    public boolean esActual(CambioEstado cambio) {
        return cambio.esActual();
    }
}