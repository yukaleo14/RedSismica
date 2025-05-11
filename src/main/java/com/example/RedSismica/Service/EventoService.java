package com.example.RedSismica.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RedSismica.Controller.DTO.EventoDTO;
import com.example.RedSismica.Controller.MAPPER.EventoMapper;
import com.example.RedSismica.Model.Evento;
import com.example.RedSismica.Repository.EventoRepository;

import jakarta.transaction.Transactional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private EventoMapper eventoMapper;

    @Transactional
    public void actualizarEvento(Long id, EventoDTO eventoDTO) {
        Optional<Evento> eventoOptional = eventoRepository.findById(id);
        if (eventoOptional.isEmpty()) {
            throw new IllegalArgumentException("No se encontró el evento con ID: " + id);
        }
        Evento eventoExistente = eventoOptional.get();
        // Actualizar los campos permitidos (magnitud, alcance, origenGeneracion)
        eventoExistente.setMagnitud(eventoDTO.getMagnitud());
        eventoExistente.setAlcance(eventoDTO.getAlcance());
        eventoExistente.setOrigenGeneracion(eventoDTO.getOrigenGeneracion());
        eventoRepository.save(eventoExistente);
    }
}
