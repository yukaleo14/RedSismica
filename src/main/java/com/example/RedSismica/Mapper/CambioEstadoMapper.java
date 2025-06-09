package com.example.RedSismica.Mapper;

import org.springframework.stereotype.Component;

import com.example.RedSismica.DTO.CambioEstadoDTO;
import com.example.RedSismica.Model.CambioEstado;
import com.example.RedSismica.Model.EstadoEvento;

@Component
public class CambioEstadoMapper {

    public CambioEstadoDTO toDTO(CambioEstado cambio) {
        CambioEstadoDTO dto = new CambioEstadoDTO();
        dto.setId(cambio.getId());
        dto.setFechaHoraInicio(cambio.getFechaHoraInicio());
        dto.setFechaHoraFin(cambio.getFechaHoraFin());

        if (cambio.getEstadoEvento() != null) {
            dto.setEstadoEventoId(cambio.getEstadoEvento().getId());
        }

        return dto;
    }

    public CambioEstado toEntity(CambioEstadoDTO dto, EstadoEvento estadoEvento) {
        CambioEstado cambio = new CambioEstado();
        cambio.setId(dto.getId());
        cambio.setFechaHoraInicio(dto.getFechaHoraInicio());
        cambio.setFechaHoraFin(dto.getFechaHoraFin());
        cambio.setEstadoEvento(estadoEvento);
        return cambio;
    }
}
