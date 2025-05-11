package com.example.RedSismica.Controller.MAPPER;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.RedSismica.Controller.DTO.EventoDTO;
import com.example.RedSismica.Model.Evento;

@Mapper(componentModel = "spring")
public interface EventoMapper {
    
    @Mapping(source = "estadoEvento.nombre", target = "estadoEvento")
    EventoDTO toDTO(Evento eventos);
}
