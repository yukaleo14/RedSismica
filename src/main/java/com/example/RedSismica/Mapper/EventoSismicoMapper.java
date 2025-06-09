package com.example.RedSismica.Mapper;

import org.springframework.stereotype.Component;

import com.example.RedSismica.DTO.EventoSismicoDTO;
import com.example.RedSismica.Model.Clasificacion;
import com.example.RedSismica.Model.EstadoEvento;
import com.example.RedSismica.Model.EventoSismico;
import com.example.RedSismica.Model.SerieTemporal;

@Component
public class EventoSismicoMapper {

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

        if (entity.getEstadoEvento() != null) {
            dto.setEstadoEventoId(entity.getEstadoEvento().getId());
        }

        if (entity.getSerieTemporal() != null) {
            dto.setSerieTemporalId(entity.getSerieTemporal().getId());
        }

        if (entity.getClasificacion() != null) {
            dto.setClasificacionId(entity.getClasificacion().getId());
        }

        return dto;
    }

    public EventoSismico toEntity(EventoSismicoDTO dto, EstadoEvento estadoEvento, SerieTemporal serieTemporal, Clasificacion clasificacion) {
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
        entity.setEstadoEvento(estadoEvento);
        entity.setSerieTemporal(serieTemporal);
        entity.setClasificacion(clasificacion);
        return entity;
    }
}
