package com.example.RedSismica.Service;

import org.springframework.stereotype.Service;

import com.example.RedSismica.Model.EstadoEvento;

@Service
public class EstadoEventoService {
    public boolean esBloqueadoEnRevision(EstadoEvento estado) {
        return estado.esBloqueadoEnPeticion();
    }

    public EstadoEvento getById(Long nuevoEstadoId) {
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }
}
