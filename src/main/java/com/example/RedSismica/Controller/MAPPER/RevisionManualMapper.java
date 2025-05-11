package com.example.RedSismica.Controller.MAPPER;


import com.example.RedSismica.Controller.DTO.RegistroRevisionManualDTO;
import com.example.RedSismica.Model.RevisionManual;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface RevisionManualMapper {
    @Mapping(source = "eventoId", target = "evento.id")
    @Mapping(source = "revisorId", target = "revisor.id")
    RevisionManual toEntity(RegistroRevisionManualDTO dto);

    @Mapping(source = "evento.id", target = "eventoId")
    @Mapping(source = "revisor.id", target = "revisorId")
    RegistroRevisionManualDTO toDTO(RevisionManual entity);
}
