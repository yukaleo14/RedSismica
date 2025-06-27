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

    //----------------------------------------------------------------------------------------------------------------
    // 1. Obtener eventos autodetectados y pendientes de revisión
    @GetMapping("/pendientes")
    //public ResponseEntity<List<EventoSismicoDTO>> buscarEventosSismicosAutoDetectado() {
    //List<EventoSismico> eventos = obtenerEventosSismicosPendientes();
    //List<EventoSismicoDTO> dtoList = eventos.stream()
     //   .map(eventoMapper::toDTO)
       // .collect(Collectors.toList());
    //return ResponseEntity.ok(dtoList);
    //}
    public ResponseEntity<List<EventoSismicoDTO>> buscarEventosSismicosAutoDetectado() {
        List<EventoSismico> eventos = eventoSismicoRepository.findByAutoDetectadoTrueOrPendienteRevisionTrue();
        List<EventoSismicoDTO> dtoList = eventos.stream()
                .map(eventoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }


    // 2. Obtener datos de un evento por ID
    @GetMapping("/{id}")
    //public ResponseEntity<EventoSismicoDTO> buscarDatosSismicos(@PathVariable Long id) {
        //EventoSismico evento = eventoService.getById(id);
      //  return ResponseEntity.ok(eventoMapper.toDTO(evento));
    //}

    public ResponseEntity<EventoSismicoDTO> buscarDatosSismicos(@PathVariable Long id) {
        EventoSismico evento = eventoSismicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado con ID: " + id));
        return ResponseEntity.ok(eventoMapper.toDTO(evento));
    }

    // 3. Finalizar estado actual y registrar nuevo cambio de estado
    @PostMapping("/{id}/cambiar-estado")
    //public ResponseEntity<CambioEstadoDTO> crearCambioEstado(@PathVariable Long id, @RequestParam Long nuevoEstadoId) {
      //  EventoSismico evento = eventoService.getById(id);
        //CambioEstado actual = cambioEstadoService.getCambioEstadoActual(evento);
        //cambioEstadoService.finalizarCambio(actual);

        //EstadoEvento nuevoEstado = estadoEventoService.getById(nuevoEstadoId);
        //CambioEstado nuevoCambio = new CambioEstado();
        //nuevoCambio.setEstadoEvento(nuevoEstado);
        //nuevoCambio.setFechaHoraInicio(LocalDateTime.now());

        //CambioEstado guardado = cambioEstadoService.crearCambioEstado(nuevoCambio);
        //return ResponseEntity.ok(cambioEstadoMapper.toDTO(guardado));
    //}
    public ResponseEntity<CambioEstadoDTO> crearCambioEstado(@PathVariable Long id, @RequestBody CambioEstadoDTO cambioEstadoDto) {
        EventoSismico evento = eventoSismicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado con ID: " + id));

        CambioEstado actual = cambioEstadoService.getCambioEstadoActual(evento);
        cambioEstadoService.finalizarCambio(actual);

        EstadoEvento nuevoEstado = estadoEventoService.getById(cambioEstadoDto.getNuevoEstadoId()); //NO SE QUE MIERDA HACER CON ESTO
        CambioEstado nuevoCambio = cambioEstadoMapper.toEntity(cambioEstadoDto, nuevoEstado);
        nuevoCambio.setFechaHoraInicio(LocalDateTime.now());
        nuevoCambio.setEventoSismico(evento);
        nuevoCambio.setEstadoEvento(nuevoEstado);

        CambioEstado guardado = cambioEstadoService.crearCambioEstado(nuevoCambio);
        return ResponseEntity.ok(cambioEstadoMapper.toDTO(guardado));
    }

    // 4. Clasificar evento
    @PostMapping("/{id}/clasificar")
    //public ResponseEntity<ClasificacionDTO> clasificarInformacion(@PathVariable Long id, @RequestBody ClasificacionDTO dto) {
    //    Clasificacion clasificacion = clasificacionMapper.toEntity(dto);
    //    Clasificacion guardada = clasificacionService.clasificarInformacion(clasificacion);
    //    eventoService.obtenerClasificacion(guardada);
    //    return ResponseEntity.ok(clasificacionMapper.toDTO(guardada));
    //}
    public ResponseEntity<ClasificacionDTO> clasificarInformacion(@PathVariable Long id, @RequestBody ClasificacionDTO dto) {
        Clasificacion clasificacion = clasificacionMapper.toEntity(dto);
        //clasificacion.setEventoSismico(evento);
        Clasificacion guardada = clasificacionService.clasificarInformacion(clasificacion);
        return ResponseEntity.ok(clasificacionMapper.toDTO(guardada));
    }

    // 5. Obtener series temporales de un evento
    @GetMapping("/{id}/series-temporales")
    //public ResponseEntity<List<SerieTemporalDTO>> obtenerSeriesTemporales(@PathVariable Long id) {
    //    try {
    //        EventoSismico evento = eventoService.getById(id);
    //        if (evento == null) {
    //            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    //        }
    //        List<SerieTemporalDTO> dtos = serieTemporalService.obtenerSeries(evento).stream()
    //                .map(serieTemporalMapper::toDTO)
    //                .collect(Collectors.toList());
    //        return ResponseEntity.ok(dtos);
    //    } catch (Exception e) {
    //        // Loggear la excepción para depuración
    //        e.printStackTrace();
    //        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    //                .body(null); // O un mensaje de error personalizado
    //    }
    //}
    public ResponseEntity<List<SerieTemporalDTO>> obtenerSeriesTemporales(@PathVariable Long id) {
        try {
        EventoSismico evento = eventoSismicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado con ID: " + id));
        List<SerieTemporalDTO> dtos = serieTemporalService.obtenerSeries(evento).stream()
                .map(serieTemporalMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
        }catch (Exception e) {
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

    @PostMapping("/{id}/procesar-evento")
    public ResponseEntity<Void> invocarCU(@PathVariable Long id) {
        sismogramaService.procesarSismograma(id);
        return ResponseEntity.ok().build();
    }



    // 7. Obtener estaciones sismológicas asociadas a un evento
    @GetMapping("/{id}/estaciones")
    //public ResponseEntity<List<EstacionSismologicaDTO>> obtenerEstaciones(@PathVariable Long id) {
        // 1. Obtener el evento por su ID
    //    EventoSismico evento = eventoService.getById(id);

        // Manejo de caso si el evento no se encuentra
    //    if (evento == null) {
    //        return ResponseEntity.notFound().build(); // Devuelve 404 Not Found
    //    }

        // 2. Obtener la única estación asociada a ese evento
  //      EstacionSismologica estacionEntity = evento.getEstacionSismologica();

        // 3. Crear una lista para almacenar el DTO de la estación.
        // Esta lista contendrá 0 elementos (si no hay estación asociada) o 1 elemento.
    //    List<EstacionSismologicaDTO> dtos = new ArrayList<>();

        // 4. Si hay una estación asociada, mapearla a DTO y añadirla a la lista
    //    if (estacionEntity != null) {
   //         EstacionSismologicaDTO estacionDto = estacionMapper.toDTO(estacionEntity);
     //       dtos.add(estacionDto);
    //    }

        // 5. Retornar la respuesta HTTP 200 OK con la lista de DTOs
    //    return ResponseEntity.ok(dtos);
    //}
    public ResponseEntity<List<EstacionSismologicaDTO>> obtenerEstaciones(@PathVariable Long id) {
        // 1. Obtener el evento por su ID
        EventoSismico evento = eventoSismicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado con ID: " + id));

        // 2. Obtener la única estación asociada a ese evento
        EstacionSismologica estacionEntity = evento.getEstacionSismologica();

        // 3. Crear una lista para almacenar el DTO de la estación.
        // Esta lista contendrá 0 elementos (si no hay estación asociada) o 1 elemento.
        List<EstacionSismologicaDTO> dtos = new ArrayList<>();

        // 4. Si hay una estación asociada, mapearla a DTO y añadirla a la lista
        if (estacionEntity != null) {
            EstacionSismologicaDTO estacionDto = estacionMapper.toDTO(estacionEntity);
            dtos.add(estacionDto);
        }

        // 5. Retornar la respuesta HTTP 200 OK con la lista de DTOs
        return ResponseEntity.ok(dtos);
    }



    //8.Actualizar Datos Específicos de un Evento Sísmico
     @PutMapping("/{id}")
    public ResponseEntity<EventoSismico> cambiarEstadoEventoSismico(
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
