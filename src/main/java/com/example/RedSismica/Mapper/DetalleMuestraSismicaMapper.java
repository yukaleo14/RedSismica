package com.example.RedSismica.Mapper;

import org.springframework.stereotype.Component;

import com.example.RedSismica.DTO.DetalleMuestraSismicaDTO;
import com.example.RedSismica.Model.DetalleMuestraSismica;
import com.example.RedSismica.Model.TipoDeDato;

@Component
public class DetalleMuestraSismicaMapper {

    public DetalleMuestraSismicaDTO toDTO(DetalleMuestraSismica entity) {
        if (entity == null) return null;

        DetalleMuestraSismicaDTO dto = new DetalleMuestraSismicaDTO();
        dto.setId(entity.getId());
        dto.setValor(entity.getValor());
        if (entity.getTipoDeDato() != null) {
            dto.setTipoDeDatoId(entity.getTipoDeDato().getId());
        }
        return dto;
    }

    public DetalleMuestraSismica toEntity(DetalleMuestraSismicaDTO dto) {
        if (dto == null) return null;

        DetalleMuestraSismica entity = new DetalleMuestraSismica();
        entity.setId(dto.getId());
        entity.setValor(dto.getValor());
        if (dto.getTipoDeDatoId() != null) {
            TipoDeDato tipoDeDato = new TipoDeDato();
            tipoDeDato.setId(dto.getTipoDeDatoId());
            entity.setTipoDeDato(tipoDeDato);
        }

        return entity;
    }
}
