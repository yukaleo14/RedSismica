package com.example.RedSismica.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.RedSismica.DTO.CambioEstadoDTO;
import com.example.RedSismica.DTO.ClasificacionDTO;
import com.example.RedSismica.DTO.EstacionSismologicaDTO;
import com.example.RedSismica.DTO.EventoSismicoDTO;
import com.example.RedSismica.DTO.MuestraSismicaDTO;
import com.example.RedSismica.DTO.SerieTemporalDTO;
import com.example.RedSismica.Mapper.CambioEstadoMapper;
import com.example.RedSismica.Mapper.ClasificacionMapper;
import com.example.RedSismica.Mapper.EstacionSismologicaMapper;
import com.example.RedSismica.Mapper.EventoSismicoMapper;
import com.example.RedSismica.Mapper.MuestraSismicaMapper;
import com.example.RedSismica.Mapper.SerieTemporalMapper;
import com.example.RedSismica.Model.CambioEstado;
import com.example.RedSismica.Model.Clasificacion;
import com.example.RedSismica.Model.EstacionSismologica;
import com.example.RedSismica.Model.EstadoEvento;
import com.example.RedSismica.Model.EventoSismico;
import com.example.RedSismica.Model.SerieTemporal;
import com.example.RedSismica.Repository.EventoSismicoRepository;
import com.example.RedSismica.Usuario.Usuario;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EventoSismicoService {
    private final EventoSismicoMapper eventoMapper;
    private final CambioEstadoService cambioEstadoService;
    private final CambioEstadoMapper cambioEstadoMapper;
    private final ClasificacionService clasificacionService;
    private final ClasificacionMapper clasificacionMapper;
    private final EstadoEventoService estadoEventoService;
    private final SerieTemporalService serieTemporalService;
    private final SerieTemporalMapper serieTemporalMapper;
    private final MuestraSismicaService muestraSismicaService;
    private final MuestraSismicaMapper muestraSismicaMapper;
    //private final EstacionSismologicaService estacionService;
    private final EstacionSismologicaMapper estacionMapper;
    private final EventoSismicoRepository eventoSismicoRepository;
    private final SismogramaService sismogramaService;

    @Autowired
    public EventoSismicoService(
            EventoSismicoMapper eventoMapper,
            CambioEstadoService cambioEstadoService,
            CambioEstadoMapper cambioEstadoMapper,
            ClasificacionService clasificacionService,
            ClasificacionMapper clasificacionMapper,
            EstadoEventoService estadoEventoService,
            SerieTemporalService serieTemporalService,
            SerieTemporalMapper serieTemporalMapper,
            MuestraSismicaService muestraSismicaService,
            MuestraSismicaMapper muestraSismicaMapper,
            EstacionSismologicaMapper estacionMapper,
            EventoSismicoRepository eventoSismicoRepository,
            SismogramaService sismogramaService
    ) {
        this.eventoMapper = eventoMapper;
        this.cambioEstadoService = cambioEstadoService;
        this.cambioEstadoMapper = cambioEstadoMapper;
        this.clasificacionService = clasificacionService;
        this.clasificacionMapper = clasificacionMapper;
        this.estadoEventoService = estadoEventoService;
        this.serieTemporalService = serieTemporalService;
        this.serieTemporalMapper = serieTemporalMapper;
        this.muestraSismicaService = muestraSismicaService;
        this.muestraSismicaMapper = muestraSismicaMapper;
        this.estacionMapper = estacionMapper;
        this.eventoSismicoRepository = eventoSismicoRepository;
        this.sismogramaService = sismogramaService;
    }

    public List<EventoSismico> obtenerEventosSismicosPendientes() {
        // TODO Auto-generated method stub
        return eventoSismicoRepository.findByEstado("PENDIENTE");
    }
    public EventoSismico getById(Long id) {
    return eventoSismicoRepository.findById(id)
            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Evento con ID " + id + " no encontrado"));
    }

    public String obtenerClasificacion(Clasificacion clasificacion) {
        return clasificacion.getNombre();
    }

    

}
