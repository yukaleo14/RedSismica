package com.example.RedSismica.Service;

import org.springframework.stereotype.Service;

import com.example.RedSismica.Model.EstadoEvento;

@Service
public class EstadoEventoService {
    public boolean esBloqueadoEnRevision(EstadoEvento estado) {
        return estado.esBloqueadoEnPeticion();
    }

    public boolean esAutoDetectado(EstadoEvento estado) {
        return estado.esAutoDetectado();
    }

    public boolean esPendienteRevision(EstadoEvento estado) {
        return estado.esPendienteRevision();
    }

        //verificamos si el estado es ambito de evento sismico
    public boolean esAmbitoEventoSismico(EstadoEvento estado) {
        if (estado.getAmbito().equals("EventoSismico")) {
            return true;
        } else {
            return false;
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
