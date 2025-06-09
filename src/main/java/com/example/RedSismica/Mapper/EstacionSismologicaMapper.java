package com.example.RedSismica.Mapper;

import org.springframework.stereotype.Component;

import com.example.RedSismica.DTO.EstacionSismologicaDTO;
import com.example.RedSismica.Model.EstacionSismologica;

@Component
public class EstacionSismologicaMapper {

    public EstacionSismologicaDTO toDTO(EstacionSismologica entity) {
        if (entity == null) return null;

        EstacionSismologicaDTO dto = new EstacionSismologicaDTO();
        dto.setCodigoEstacion(entity.getCodigoEstacion());
        dto.setNombre(entity.getNombre());
        dto.setLatitud(entity.getLatitud());
        dto.setLongitud(entity.getLongitud());
        dto.setFechaSolicitudCertificacion(entity.getFechaSolicitudCertificacion());
        dto.setNroCerificacionAdquisicion(entity.getNroCerificacionAdquisicion());
        return dto;
    }

    public EstacionSismologica toEntity(EstacionSismologicaDTO dto) {
        if (dto == null) return null;

        EstacionSismologica entity = new EstacionSismologica();
        entity.setCodigoEstacion(dto.getCodigoEstacion());
        entity.setNombre(dto.getNombre());
        entity.setLatitud(dto.getLatitud());
        entity.setLongitud(dto.getLongitud());
        entity.setFechaSolicitudCertificacion(dto.getFechaSolicitudCertificacion());
        entity.setNroCerificacionAdquisicion(dto.getNroCerificacionAdquisicion());
        return entity;
    }
}