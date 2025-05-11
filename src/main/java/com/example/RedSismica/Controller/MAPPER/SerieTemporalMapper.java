package com.example.RedSismica.Controller.MAPPER;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.RedSismica.Controller.DTO.SerieTemporalDTO;
import com.example.RedSismica.Model.SerieTemporal;

@Mapper(componentModel = "spring")
public interface SerieTemporalMapper {

    @Mapping(source = "estacionSismologica.id", target = "estacionSismologicaId")
    @Mapping(source = "estacionSismologica.codigo", target = "estacionSismologicaCodigo")
    SerieTemporalDTO toDTO(SerieTemporal serieTemporal);
}
