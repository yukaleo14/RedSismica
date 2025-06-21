package com.example.RedSismica.Mapper;

import org.springframework.stereotype.Component;

import com.example.RedSismica.DTO.EventoSismicoDTO;
import com.example.RedSismica.DTO.SerieTemporalDTO; // Asegúrate de que esta importación exista
import com.example.RedSismica.Model.Clasificacion;
import com.example.RedSismica.Model.EstadoEvento;
import com.example.RedSismica.Model.EventoSismico;
import com.example.RedSismica.Model.SerieTemporal;

import java.time.LocalDateTime; // Necesario para la fecha de revisión
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventoSismicoMapper {

    private final SerieTemporalMapper serieTemporalMapper;

    // Asegúrate de que SerieTemporalMapper esté correctamente inyectado
    public EventoSismicoMapper(SerieTemporalMapper serieTemporalMapper) {
        this.serieTemporalMapper = serieTemporalMapper;
    }

    public EventoSismicoDTO toDTO(EventoSismico entity) {
        if (entity == null) return null;

        EventoSismicoDTO dto = new EventoSismicoDTO();
        dto.setId(entity.getId());
        dto.setFechaHoraOcurrencia(entity.getFechaHoraOcurrencia());
        dto.setFechaHoraFin(entity.getFechaHoraFin());
        dto.setLatitudHipocentro(entity.getLatitudHipocentro());
        dto.setLatitudEpicentro(entity.getLatitudEpicentro());
        dto.setLongitudEpicentro(entity.getLongitudEpicentro());
        dto.setLongitudHipocentro(entity.getLongitudHipocentro());
        dto.setMagnitud(entity.getMagnitud());
        dto.setAlcance(entity.getAlcance());
        dto.setOrigenGeneracion(entity.getOrigenGeneracion());
        dto.setAutoDetectado(entity.getAutoDetectado());
        dto.setPendienteRevision(entity.getPendienteRevision());
        // AÑADIR estas líneas para mapear los campos de revisión al DTO
        dto.setResponsableRevision(entity.getResponsableRevision());
        dto.setFechaHoraRevision(entity.getFechaHoraRevision()); // Asumiendo que ahora existe este getter en el Model

        if (entity.getEstadoEvento() != null) {
            dto.setEstadoEventoId(entity.getEstadoEvento().getId());
        }

        // Mapeo de lista serie temporal
        // CORRECCIÓN: 'getSeriesTemporales()' con 'T' mayúscula y usar el serieTemporalMapper
        if (entity.getSeriesTemporales() != null && !entity.getSeriesTemporales().isEmpty()) {
            dto.setSeriesTemporales(
                entity.getSeriesTemporales().stream()
                    .map(serieTemporalMapper::toDTO) // <-- Aquí usamos el serieTemporalMapper
                    .collect(Collectors.toList())
            );
        } else {
            dto.setSeriesTemporales(List.of());
        }

        if (entity.getClasificacion() != null) {
            dto.setClasificacionId(entity.getClasificacion().getId());
        }

        return dto;
    }

    public EventoSismico toEntity(EventoSismicoDTO dto /*, EstadoEvento estadoEvento, Clasificacion clasificacion*/) {
        if (dto == null) return null;

        EventoSismico entity = new EventoSismico();
        entity.setId(dto.getId());
        entity.setFechaHoraOcurrencia(dto.getFechaHoraOcurrencia());
        entity.setFechaHoraFin(dto.getFechaHoraFin());
        entity.setLatitudHipocentro(dto.getLatitudHipocentro());
        entity.setLatitudEpicentro(dto.getLatitudEpicentro());
        entity.setLongitudEpicentro(dto.getLongitudEpicentro());
        entity.setLongitudHipocentro(dto.getLongitudHipocentro());
        entity.setMagnitud(dto.getMagnitud());
        entity.setAlcance(dto.getAlcance());
        entity.setOrigenGeneracion(dto.getOrigenGeneracion());
        entity.setAutoDetectado(dto.getAutoDetectado());
        entity.setPendienteRevision(dto.getPendienteRevision());
        // AÑADIR estas líneas para mapear los campos de revisión desde el DTO a la entidad
        entity.setResponsableRevision(dto.getResponsableRevision());
        entity.setFechaHoraRevision(dto.getFechaHoraRevision()); // Asumiendo que ahora existe este getter en el DTO

        // Las relaciones (estadoEvento, clasificacion, seriesTemporales) se manejan en la capa de servicio
        // o se inyectan en este mapper para mapearlos. Por ahora, asumimos que se hará en el servicio.

        return entity;
    }
}