package com.example.RedSismica.Mapper;

import com.example.RedSismica.DTO.DetalleMuestraSismicaDTO;
import com.example.RedSismica.DTO.MuestraSismicaDTO;
import com.example.RedSismica.Model.DetalleMuestraSismica;
import com.example.RedSismica.Model.MuestraSismica;

public class MuestraSismicaMapper {
    
    public MuestraSismicaDTO toDTO(MuestraSismica entity) {
        if (entity == null) return null;

        MuestraSismicaDTO dto = new MuestraSismicaDTO();
        dto.setId(entity.getId());
        dto.setFechaHoraMuestra(entity.getFechaHoraMuestra());
        dto.setVelocidad(entity.getVelocidad());
        dto.setFrecuencia(entity.getFrecuencia());
        dto.setLongitud(entity.getLongitud());

        dto.setDetalleMuestraSismica(toDetalleDTO(entity.getDetalleMuestraSismica()));

        return dto;
    }

    public MuestraSismica toEntity(MuestraSismicaDTO dto) {
        if (dto == null) return null;

        MuestraSismica entity = new MuestraSismica();
        entity.setId(dto.getId());
        entity.setFechaHoraMuestra(dto.getFechaHoraMuestra());
        entity.setVelocidad(dto.getVelocidad());
        entity.setFrecuencia(dto.getFrecuencia());
        entity.setLongitud(dto.getLongitud());

        entity.setDetalleMuestraSismica(toDetalleEntity(dto.getDetalleMuestraSismica()));

        return entity;
    }

    private DetalleMuestraSismicaDTO toDetalleDTO(DetalleMuestraSismica entity) {
        if (entity == null) return null;

        DetalleMuestraSismicaDTO dto = new DetalleMuestraSismicaDTO();
        dto.setId(entity.getId());
        // Mapear más campos si existen
        return dto;
    }

    private DetalleMuestraSismica toDetalleEntity(DetalleMuestraSismicaDTO dto) {
        if (dto == null) return null;

        DetalleMuestraSismica entity = new DetalleMuestraSismica();
        entity.setId(dto.getId());
        // Mapear más campos si existen
        return entity;
    } 
}
