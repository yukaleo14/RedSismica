package com.example.RedSismica.Mapper;

import org.springframework.stereotype.Component;

import com.example.RedSismica.DTO.ClasificacionDTO;
import com.example.RedSismica.Model.Clasificacion;

@Component
public class ClasificacionMapper {

    public ClasificacionDTO toDTO(Clasificacion entity) {
        if (entity == null) return null;

        ClasificacionDTO dto = new ClasificacionDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        return dto;
    }

    public Clasificacion toEntity(ClasificacionDTO dto) {
        if (dto == null) return null;

        Clasificacion entity = new Clasificacion();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        return entity;
    }
}