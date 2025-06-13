package com.example.RedSismica.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RedSismica.Model.Clasificacion;
import com.example.RedSismica.Model.EventoSismico;
import com.example.RedSismica.Repository.EventoSismicoRepository;

@Service
public class EventoSismicoService {
    
    @Autowired private EventoSismicoRepository repo;

    public List<EventoSismico> buscarAutoDetectados() {
        return repo.findByAutoDetectadoTrue();
    }

    public boolean esAutoDetectado(EventoSismico evento) {
        return evento.esAutoDetectado();
    }

    public boolean esPendienteRevision(EventoSismico evento) {
        return evento.esPendienteRevision();
    }

    public LocalDateTime getFechaHoraOcurrencia(EventoSismico evento) {
        return evento.getFechaHoraOcurrencia();
    }

    public double getLatitudEpicentro(EventoSismico evento) {
        return evento.getLatitudEpicentro();
    }

    public double getLongitudEpicentro(EventoSismico evento) {
        return evento.getLongitudEpicentro();
    }

    public double getLatitudHipocentro(EventoSismico evento) {
        return evento.getLatitudHipocentro();
    }

    public double getLongitudHipocentro(EventoSismico evento) {
        return evento.getLongitudHipocentro();
    }

    public double getMagnitud(EventoSismico evento) {
        return evento.getMagnitud();
    }

    public void asociarClasificacion(Long id, Clasificacion guardada) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'asociarClasificacion'");
    }

    public EventoSismico getById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }
}
