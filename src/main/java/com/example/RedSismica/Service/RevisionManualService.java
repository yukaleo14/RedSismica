package com.example.RedSismica.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RedSismica.Controller.DTO.EventoDTO;
import com.example.RedSismica.Controller.DTO.RegistroRevisionManualDTO;
import com.example.RedSismica.Controller.DTO.SerieTemporalDTO;
import com.example.RedSismica.Controller.MAPPER.EventoMapper;
import com.example.RedSismica.Controller.MAPPER.RevisionManualMapper;
import com.example.RedSismica.Controller.MAPPER.SerieTemporalMapper;
import com.example.RedSismica.Model.EstadoEvento;
import com.example.RedSismica.Model.EventoSismico;
import com.example.RedSismica.Model.RevisionManual;
import com.example.RedSismica.Model.SerieTemporal;
import com.example.RedSismica.Repository.EstadoEventoRepository;
import com.example.RedSismica.Repository.EventoRepository;
import com.example.RedSismica.Repository.RevisionManualRepository;
import com.example.RedSismica.Repository.SerieTemporalRepository;
import com.example.RedSismica.Repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class RevisionManualService {

    private final EventoRepository eventoRepository;
    private final RevisionManualRepository revisionManualRepository;
    private final UsuarioRepository usuarioRepository;
    private final EstadoEventoRepository estadoEventoRepository;
    private final RevisionManualMapper revisionManualMapper;
    private final EventoMapper eventoMapper;
    private final SerieTemporalRepository serieTemporalRepository;
    private final SerieTemporalMapper serieTemporalMapper;

    @Autowired
    public RevisionManualService(EventoRepository eventoRepository, RevisionManualRepository revisionManualRepository, UsuarioRepository usuarioRepository, EstadoEventoRepository estadoEventoRepository, RevisionManualMapper revisionManualMapper, EventoMapper eventoMapper, SerieTemporalRepository serieTemporalRepository, SerieTemporalMapper serieTemporalMapper) {
        this.eventoRepository = eventoRepository;
        this.revisionManualRepository = revisionManualRepository;
        this.usuarioRepository = usuarioRepository;
        this.estadoEventoRepository = estadoEventoRepository;
        this.revisionManualMapper = revisionManualMapper;
        this.eventoMapper = eventoMapper;
        this.serieTemporalRepository = serieTemporalRepository;
        this.serieTemporalMapper = serieTemporalMapper;
    }

    public List<EventoDTO> obtenerEventosPendientesRevision() {
        List<EventoSismico> eventos = eventoRepository.findByEstadoEvento_NombreOrderByFechaHoraOrigenDesc("Registrado");
        return eventos.stream()
                .map(eventoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EventoDTO obtenerDetallesEvento(Long eventoId) {
        Optional<EventoSismico> eventoOptional = eventoRepository.findById(eventoId);
        if (eventoOptional.isEmpty()) {
            throw new IllegalArgumentException("No se encontró el evento con ID: " + eventoId);
        }
        EventoSismico evento = eventoOptional.get();
        EventoDTO eventoDTO = eventoMapper.toDTO(evento);
        List<SerieTemporal> seriesTemporales = serieTemporalRepository.findByEvento_IdOrderByInstanteTiempo(eventoId);
        List<SerieTemporalDTO> seriesTemporalesDTO = seriesTemporales.stream()
                .map(serieTemporalMapper::toDTO)
                .collect(Collectors.toList());
        // Aquí podrías agregar la lista de seriesTemporalesDTO al EventoDTO o devolver un DTO separado
        // para los detalles del evento. Por ahora, solo las obtenemos.
        return eventoDTO;
    }

    @Transactional
    public void registrarResultadoRevisionManual(RegistroRevisionManualDTO registroDTO) {
        Optional<EventoSismico> eventoOptional = eventoRepository.findById(registroDTO.getEventoId());
        if (eventoOptional.isEmpty()) {
            throw new IllegalArgumentException("No se encontró el evento con ID: " + registroDTO.getEventoId());
        }

        if (usuarioRepository.findById(registroDTO.getRevisorId()).isEmpty()) {
            throw new IllegalArgumentException("No se encontró el revisor con ID: " + registroDTO.getRevisorId());
        }

        EventoSismico evento = eventoOptional.get();
        EstadoEvento estadoEnRevision = estadoEventoRepository.findByNombre("En Revisión");
        if (estadoEnRevision == null) {
            throw new IllegalStateException("No se encontró el estado 'En Revisión'.");
        }
        evento.setEstadoEvento(estadoEnRevision);
        eventoRepository.save(evento);

        RevisionManual revisionManual = revisionManualMapper.toEntity(registroDTO);
        revisionManual.setEvento(evento);
        revisionManual.setFechaRevision(LocalDateTime.now());
        revisionManualRepository.save(revisionManual);

        // Actualizar el estado del evento según el resultado de la revisión
        EstadoEvento nuevoEstado = null;
        switch (registroDTO.getResultadoRevision().toLowerCase()) {
            case "aprobado":
                nuevoEstado = estadoEventoRepository.findByNombre("Confirmado");
                break;
            case "rechazado":
                nuevoEstado = estadoEventoRepository.findByNombre("Rechazado");
                break;
            case "solicitar revisión a experto":
                nuevoEstado = estadoEventoRepository.findByNombre("Pendiente Experto");
                break;
        }

        if (nuevoEstado != null) {
            evento.setEstadoEvento(nuevoEstado);
            eventoRepository.save(evento);
        }
    }
}
