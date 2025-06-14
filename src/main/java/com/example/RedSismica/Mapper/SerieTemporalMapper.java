package com.example.RedSismica.Mapper;

import java.util.List;

import com.example.RedSismica.DTO.EstacionSismologicaDTO;
import com.example.RedSismica.DTO.EventoSismicoDTO;
import com.example.RedSismica.DTO.SerieTemporalDTO;
import com.example.RedSismica.Model.EstacionSismologica;
import com.example.RedSismica.Model.EventoSismico;
import com.example.RedSismica.Model.SerieTemporal;

public class SerieTemporalMapper {
    
private MuestraSismicaMapper muestraSismicaMapper;

 public SerieTemporalDTO toDTO(SerieTemporal entity) {
        if (entity == null) return null;

        SerieTemporalDTO dto = new SerieTemporalDTO();
        dto.setId(entity.getId());
        dto.setFechaHoraRegistroMuestra(entity.getFechaHoraRegistroMuestra());
        dto.setFechaHoraRegristo(entity.getFechaHoraRegristo());
        dto.setFrecuenciaMuestreo(entity.getFrecuenciaMuestreo());
        dto.setCondicionAlarma(entity.getCondicionAlarma());

        dto.setEvento(toEventoDTO(entity.getEvento()));
        dto.setEstacionSismologica(toEstacionDTO(entity.getEstacionSismologica()));
        dto.setMuestraSismica(muestraSismicaMapper.toDTO(entity.getMuestraSismica().get(0)));

        return dto;
    }

    public SerieTemporal toEntity(SerieTemporalDTO dto) {
        if (dto == null) return null;

        SerieTemporal entity = new SerieTemporal();
        entity.setId(dto.getId());
        entity.setFechaHoraRegistroMuestra(dto.getFechaHoraRegistroMuestra());
        entity.setFechaHoraRegristo(dto.getFechaHoraRegristo());
        entity.setFrecuenciaMuestreo(dto.getFrecuenciaMuestreo());
        entity.setCondicionAlarma(dto.getCondicionAlarma());

        entity.setEvento(toEventoEntity(dto.getEvento()));
        entity.setEstacionSismologica(toEstacionEntity(dto.getEstacionSismologica()));
        entity.setMuestraSismica(List.of(muestraSismicaMapper.toEntity(dto.getMuestraSismica())));

        return entity;
    }

    private EventoSismicoDTO toEventoDTO(EventoSismico entity) {
        if (entity == null) return null;
        EventoSismicoDTO dto = new EventoSismicoDTO();
        dto.setId(entity.getId());
        return dto;
    }

    private EventoSismico toEventoEntity(EventoSismicoDTO dto) {
        if (dto == null) return null;
        EventoSismico entity = new EventoSismico();
        entity.setId(dto.getId());
        return entity;
    }

    private EstacionSismologicaDTO toEstacionDTO(EstacionSismologica entity) {
        if (entity == null) return null;
        EstacionSismologicaDTO dto = new EstacionSismologicaDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre()); // si tiene
        return dto;
    }

    private EstacionSismologica toEstacionEntity(EstacionSismologicaDTO dto) {
        if (dto == null) return null;
        EstacionSismologica entity = new EstacionSismologica();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre()); // si tiene
        return entity;
    }
}
