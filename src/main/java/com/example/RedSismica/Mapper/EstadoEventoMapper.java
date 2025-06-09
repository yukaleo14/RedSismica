package com.example.RedSismica.Mapper;

import org.springframework.stereotype.Component;

import com.example.RedSismica.DTO.EstadoEventoDTO;
import com.example.RedSismica.Model.EstadoEvento;

@Component
public class EstadoEventoMapper {

    public EstadoEventoDTO toDTO(EstadoEvento entity) {
        if (entity == null) return null;

        EstadoEventoDTO dto = new EstadoEventoDTO();
        dto.setId(entity.getId());
        dto.setAmbito(entity.getAmbito());
        dto.setNombre(entity.getNombre());
        return dto;
    }

    public EstadoEvento toEntity(EstadoEventoDTO dto) {
        if (dto == null) return null;

        EstadoEvento entity = new EstadoEvento();
        entity.setId(dto.getId());
        entity.setAmbito(dto.getAmbito());
        entity.setNombre(dto.getNombre());
        return entity;
    }
}