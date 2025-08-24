package com.example.RedSismica.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RedSismica.Model.EstadoEvento;
import com.example.RedSismica.Model.EventoSismico;
import com.example.RedSismica.Repository.EventoSismicoRepository;

@Service
public class EstadoService {

    @Autowired private EventoSismicoRepository repo;

    // Metodo nro 6 --------------------------------------------------------------------------------------------------
    // Metodo que obtiene los estados "AutoDetectado" de las entidades EventoSismico
    public List<EventoSismico> esAutoDetectado() {
        return repo.findByAutoDetectadoTrue();
    }

    // Metodo nro 8 --------------------------------------------------------------------------------------------------
    // Metodo que obtiene los estados "PendienteRevision" de las entidades EventoSismico
    public List<EventoSismico> esPendienteRevision() {
        return repo.findByPendienteRevisionTrue();
    }

    // Metodo nro 21 --------------------------------------------------------------------------------------------------
    // Metodo que verifica si el ambito del estado es "EventoSismico"
    public boolean esAmbitoEventoSismico(EstadoEvento estado) {
        if (estado.getAmbito().equals("EventoSismico")) {
            return true;
        } else {
            return false;
        }
    }
    // Metodo nro 22 --------------------------------------------------------------------------------------------------
    // Metodo que verifica si el estado es "BloqueadoEnRevision"
    public boolean esBloqueadoEnRevision(EstadoEvento estado) {
        return estado.esBloqueadoEnRevision();
    }

    public String setEstado(EstadoEvento estado) {
        if (estado.getNombre().equals("AutoDetectado")) {
            return "El evento es autodetectado";
        } else if (estado.getNombre().equals("PendienteRevision")) {
            return "El evento está pendiente de revisión";
        } else if (estado.getNombre().equals("BloqueadoEnRevision")) {
            return "El evento está bloqueado en revisión";
        } else if (estado.getNombre().equals("Rechazado")) {
            return "El evento es rechazado";
        } else {
            return "Estado desconocido";
        }
    }

    public String esRechazado(EstadoEvento estado) {
        return estado.esRechazado();
    }

    public boolean esConfirmado(EstadoEvento estado) {
        return estado.esConfirmado();
    }

    public EstadoEvento getById(Long nuevoEstadoId) {
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }
}
