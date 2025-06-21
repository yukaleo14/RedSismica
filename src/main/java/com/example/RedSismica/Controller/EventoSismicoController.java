package com.example.RedSismica.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import com.example.RedSismica.Model.EstadoEvento;
import com.example.RedSismica.Model.EventoSismico;
import com.example.RedSismica.Model.SerieTemporal;
import com.example.RedSismica.Repository.EventoSismicoRepository;
import com.example.RedSismica.Service.CambioEstadoService;
import com.example.RedSismica.Service.ClasificacionService;
import com.example.RedSismica.Service.EstacionSismologicaService;
import com.example.RedSismica.Service.EstadoEventoService;
import com.example.RedSismica.Service.EventoSismicoService;
import com.example.RedSismica.Service.MuestraSismicaService;
import com.example.RedSismica.Service.SerieTemporalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class EventoSismicoController {

    private final EventoSismicoService eventoService;
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
    private final EstacionSismologicaService estacionService;
    private final EstacionSismologicaMapper estacionMapper;
    private final EventoSismicoRepository eventoSismicoRepository;

    // 1. Obtener eventos autodetectados y pendientes de revisión
    @GetMapping("/pendientes")
    public ResponseEntity<List<EventoSismicoDTO>> obtenerEventosPendientes() {
        List<EventoSismico> eventos = eventoService.buscarPendientes();
        List<EventoSismicoDTO> dtoList = eventos.stream()
            .map(eventoMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    // 2. Obtener datos de un evento por ID
    @GetMapping("/{id}")
    public ResponseEntity<EventoSismicoDTO> obtenerPorId(@PathVariable Long id) {
        EventoSismico evento = eventoService.getById(id);
        return ResponseEntity.ok(eventoMapper.toDTO(evento));
    }

    // 3. Finalizar estado actual y registrar nuevo cambio de estado
    @PostMapping("/{id}/cambiar-estado")
    public ResponseEntity<CambioEstadoDTO> cambiarEstado(@PathVariable Long id, @RequestParam Long nuevoEstadoId) {
        EventoSismico evento = eventoService.getById(id);
        CambioEstado actual = cambioEstadoService.getCambioEstadoActual(evento);
        cambioEstadoService.finalizarCambio(actual);

        EstadoEvento nuevoEstado = estadoEventoService.getById(nuevoEstadoId);
        CambioEstado nuevoCambio = new CambioEstado();
        nuevoCambio.setEstadoEvento(nuevoEstado);
        nuevoCambio.setFechaHoraInicio(LocalDateTime.now());

        CambioEstado guardado = cambioEstadoService.crearCambioEstado(nuevoCambio);
        return ResponseEntity.ok(cambioEstadoMapper.toDTO(guardado));
    }

    // 4. Clasificar evento
    @PostMapping("/{id}/clasificar")
    public ResponseEntity<ClasificacionDTO> clasificar(@PathVariable Long id, @RequestBody ClasificacionDTO dto) {
        Clasificacion clasificacion = clasificacionMapper.toEntity(dto);
        Clasificacion guardada = clasificacionService.clasificarInformacion(clasificacion);
        eventoService.asociarClasificacion(id, guardada);
        return ResponseEntity.ok(clasificacionMapper.toDTO(guardada));
    }

    // 5. Obtener series temporales de un evento
    @GetMapping("/{id}/series-temporales")
    public ResponseEntity<List<SerieTemporalDTO>> obtenerSeries(@PathVariable Long id) {
        /* EventoSismico evento = eventoService.getById(id);
        List<SerieTemporalDTO> dtos = serieTemporalService.obtenerSeries(evento).stream()
            .map(serieTemporalMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos); */
        try {
            EventoSismico evento = eventoService.getById(id);
            if (evento == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            List<SerieTemporalDTO> dtos = serieTemporalService.obtenerSeries(evento).stream()
                    .map(serieTemporalMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            // Loggear la excepción para depuración
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // O un mensaje de error personalizado
        }
    }

    // 6. Obtener muestras por serie temporal
    @GetMapping("/series/{idSerie}/muestras")
    public ResponseEntity<List<MuestraSismicaDTO>> obtenerMuestras(@PathVariable Long idSerie) {
        SerieTemporal serie = serieTemporalService.getById(idSerie);
        List<MuestraSismicaDTO> dtos = muestraSismicaService.obtenerMuestras(serie).stream()
            .map(muestraSismicaMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // 7. Obtener estaciones sismológicas asociadas a un evento
    @GetMapping("/{id}/estaciones")
    public ResponseEntity<List<EstacionSismologicaDTO>> obtenerEstaciones(@PathVariable Long id) {
        EventoSismico evento = eventoService.getById(id);
        List<EstacionSismologicaDTO> dtos = estacionService.obtenerEstaciones(evento).stream()
            .map(estacionMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    //8.Actualizar Datos Específicos de un Evento Sísmico
     @PutMapping("/{id}")
    public ResponseEntity<EventoSismico> actualizarEvento(
            @PathVariable Long id,
            @RequestBody EventoSismicoDTO eventoDto) {

        Optional<EventoSismico> eventoExistente = eventoSismicoRepository.findById(id);

        if (eventoExistente.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        EventoSismico evento = eventoExistente.get();

        evento.setMagnitud(eventoDto.getMagnitud());
        evento.setAlcance(eventoDto.getAlcance());
        evento.setOrigenGeneracion(eventoDto.getOrigenGeneracion());

        // Campos de revisión
        evento.setFechaHoraRevision(LocalDateTime.now());
        evento.setResponsableRevision(eventoDto.getResponsableRevision());

        try {
            EventoSismico eventoActualizado = eventoSismicoRepository.save(evento);
            return new ResponseEntity<>(eventoActualizado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}



