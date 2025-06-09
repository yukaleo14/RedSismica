package com.example.RedSismica.Mapper;

import org.springframework.stereotype.Component;

import com.example.RedSismica.DTO.TipoDeDatoDTO;
import com.example.RedSismica.Model.TipoDeDato;

@Component
public class TipoDeDatoMapper {

    public TipoDeDatoDTO toDTO(TipoDeDato entity) {
        if (entity == null) return null;

        TipoDeDatoDTO dto = new TipoDeDatoDTO();
        dto.setId(entity.getId());
        dto.setDenominacion(entity.getDenominacion());
        dto.setNombreUnidadDeMedida(entity.getNombreUnidadDeMedida());
        dto.setValorUmbral(entity.getValorUmbral());
        return dto;
    }

    public TipoDeDato toEntity(TipoDeDatoDTO dto) {
        if (dto == null) return null;

        TipoDeDato entity = new TipoDeDato();
        entity.setId(dto.getId());
        entity.setDenominacion(dto.getDenominacion());
        entity.setNombreUnidadDeMedida(dto.getNombreUnidadDeMedida());
        entity.setValorUmbral(dto.getValorUmbral());
        return entity;
    }
}
