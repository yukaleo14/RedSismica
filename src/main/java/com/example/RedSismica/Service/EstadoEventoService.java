package com.example.RedSismica.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RedSismica.Model.EstadoEvento;
import com.example.RedSismica.Model.EventoSismico;
import com.example.RedSismica.Repository.EventoSismicoRepository;

@Service
public class EstadoEventoService {

    @Autowired private EventoSismicoRepository repo;

    public boolean esBloqueadoEnRevision(EstadoEvento estado) {
        return estado.esBloqueadoEnRevision();
    }

    public List<EventoSismico> esAutoDetectado() {
        return repo.findByAutoDetectadoTrue();
    }

    public List<EventoSismico> esPendienteRevision() {
        return repo.findByPendienteRevisionTrue();
    }

    public boolean esAmbitoEventoSismico(EstadoEvento estado) {
        if (estado.getAmbito().equals("EventoSismico")) {
            return true;
        } else {
            return false;
        }
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
