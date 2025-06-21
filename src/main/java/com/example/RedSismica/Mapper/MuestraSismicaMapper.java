package com.example.RedSismica.Mapper;

import org.springframework.stereotype.Component;

import com.example.RedSismica.DTO.DetalleMuestraSismicaDTO;
import com.example.RedSismica.DTO.MuestraSismicaDTO;
import com.example.RedSismica.Model.DetalleMuestraSismica;
import com.example.RedSismica.Model.MuestraSismica;
import com.example.RedSismica.Model.TipoDeDato;

@Component
public class MuestraSismicaMapper {

    public MuestraSismicaDTO toDTO(MuestraSismica entity) {
        if (entity == null)
            return null;

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
        if (dto == null)
            return null;

        MuestraSismica entity = new MuestraSismica();
        entity.setId(dto.getId());
        entity.setFechaHoraMuestra(dto.getFechaHoraMuestra());
        entity.setVelocidad(dto.getVelocidad());
        entity.setFrecuencia(dto.getFrecuencia());
        entity.setLongitud(dto.getLongitud());

        if (dto.getDetalleMuestraSismica() != null) {
            entity.setDetalleMuestraSismica(toDetalleEntity(dto.getDetalleMuestraSismica()));
        }
        return entity;
    }

    private DetalleMuestraSismicaDTO toDetalleDTO(DetalleMuestraSismica entity) {
        if (entity == null)
            return null;

        DetalleMuestraSismicaDTO dto = new DetalleMuestraSismicaDTO();
        dto.setId(entity.getId());
        dto.setValor(entity.getValor());
        if (entity.getTipoDeDato() != null) {
            dto.setTipoDeDatoId(entity.getTipoDeDato().getId());
        }
        return dto;
    }

    private DetalleMuestraSismica toDetalleEntity(DetalleMuestraSismicaDTO dto) {
        if (dto == null)
            return null;

        DetalleMuestraSismica entity = new DetalleMuestraSismica();
        entity.setId(dto.getId());
        entity.setValor(dto.getValor());
        //No se setea TipoDeDato completo, se maneja en el servicio :)
        return entity;
    }
}
