package com.example.RedSismica.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.RedSismica.DTO.DetalleMuestraSismicaDTO;
import com.example.RedSismica.Model.DetalleMuestraSismica;

@Component
public class DetalleMuestraSismicaMapper {

    @Autowired
    private TipoDeDatoMapper tipoDeDatoMapper;

    public DetalleMuestraSismicaDTO toDTO(DetalleMuestraSismica entity) {
        if (entity == null) return null;

        DetalleMuestraSismicaDTO dto = new DetalleMuestraSismicaDTO();
        dto.setId(entity.getId());
        dto.setValor(entity.getValor());
        dto.setTipoDeDato(tipoDeDatoMapper.toDTO(entity.getTipoDeDato()));
        return dto;
    }

    public DetalleMuestraSismica toEntity(DetalleMuestraSismicaDTO dto) {
        if (dto == null) return null;

        DetalleMuestraSismica entity = new DetalleMuestraSismica();
        entity.setId(dto.getId());
        entity.setValor(dto.getValor());
        entity.setTipoDeDato(tipoDeDatoMapper.toEntity(dto.getTipoDeDato()));
        return entity;
    }
}
