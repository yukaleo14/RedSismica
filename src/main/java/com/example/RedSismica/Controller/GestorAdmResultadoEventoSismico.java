package com.example.RedSismica.Controller;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.RedSismica.Model.EstacionSismologica;
import com.example.RedSismica.Model.EstadoEvento;
import com.example.RedSismica.Model.EventoSismico;
import com.example.RedSismica.Model.SerieTemporal;
import com.example.RedSismica.Repository.EventoSismicoRepository;
import com.example.RedSismica.Service.CambioEstadoService;
import com.example.RedSismica.Service.ClasificacionService;
import com.example.RedSismica.Service.EstadoService;
import com.example.RedSismica.Service.EventoSismicoService;
import com.example.RedSismica.Service.MuestraSismicaService;
import com.example.RedSismica.Service.ResultadoRevisionDTO;
import com.example.RedSismica.Service.SerieTemporalService;
import com.example.RedSismica.Service.SesionService;
import com.example.RedSismica.Service.SismogramaService;
import com.example.RedSismica.Usuario.Usuario;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class GestorAdmResultadoEventoSismico {

    @Autowired
    private EventoSismicoRepository repo;

    private final EventoSismicoService eventoService;
    private final EventoSismicoMapper eventoMapper;
    private final CambioEstadoService cambioEstadoService;
    private final CambioEstadoMapper cambioEstadoMapper;
    private final ClasificacionService clasificacionService;
    private final ClasificacionMapper clasificacionMapper;
    private final EstadoService estadoEventoService;
    private final SerieTemporalService serieTemporalService;
    private final SerieTemporalMapper serieTemporalMapper;
    private final MuestraSismicaService muestraSismicaService;
    private final MuestraSismicaMapper muestraSismicaMapper;
    private final EstacionSismologicaMapper estacionMapper;
    private final EventoSismicoRepository eventoSismicoRepository;
    private final SismogramaService sismogramaService;
//----------------------------------------------------------------------------------------------------------------

    //Metodo nro 3 --------------------------------------------------------------------------------------------------
    // Metodo para invocar el caso de uso "Registrar Resultado de Revisión de Evento
    // Metodo invocado desde el menú principal del gestor de administración de resultados de eventos sísmicos.
    public String opcionRegistrarResultRevEventoSismico() {
        return "Opción para registrar resultados de revisión de eventos sísmicos";
    }

    //Metodo nro 4 --------------------------------------------------------------------------------------------------
    // Metodo de inicializacion para buscar eventos sísmicos autodectados o pendientes de revisión
    @GetMapping("/pendientes")
    public ResponseEntity<List<EventoSismicoDTO>> buscarEventosSismicosAutoDetectado() {
        try {
            List<EventoSismico> eventos = eventoService.buscarEventosSismicosAutoDetectado();
            List<EventoSismicoDTO> dtoList = eventos.stream()
                .map(eventoMapper::toDTO)
                .collect(Collectors.toList());
            return ResponseEntity.ok(dtoList);
        } catch (Exception e) {
            e.printStackTrace(); // Esto mostrará el error real en la consola
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Metodo nro 16 --------------------------------------------------------------------------------------------------
    // Metodo para ordenar los eventos sísmicos por fecha y hora de ocurrencia descendente
    public  List<EventoSismico> ordenarEventosPorFechaHora() {
        return repo.OrderByFechaHoraOcurrenciaDesc();
    }

    // Metodo nro 19 --------------------------------------------------------------------------------------------------
    // Metodo que tomara la seleccion del evento sísmico de la pantalla de selección
    public String tomarSeleccEventoSismico(EventoSismico evento) {
        return "Evento Sismico seleccionado: " + evento.getId();
    }

    // Metodo nro 20 --------------------------------------------------------------------------------------------------
    // Metodo que buscara el estado "BloqueadoEnRevision" de la entidad EstadoEvento
    public String buscarEstadoBloqueadoRevision(EstadoService estado) {
        EstadoService estadoService = new EstadoService();
        return "Estado Bloqueado en Revisión: " + estadoService.esBloqueadoEnRevision(EstadoEvento.BLOQUEADO);
    }

    // Metodo nro 23 --------------------------------------------------------------------------------------------------
    // Metodo que obtendra la fecha y hora actual del sistema
    public Date getFechaHoraActual () {
        return Date.valueOf(LocalDateTime.now().toLocalDate());
    }

    // Metodo nro 24 --------------------------------------------------------------------------------------------------
    // Metodo que buscara el empleado logueado en la sesion actual
    public String buscarEmpleadoLogueado(Usuario usuario) {
        return SesionService.getUsuarioLogueado(usuario);
    }

    // Metodo nro 25 --------------------------------------------------------------------------------------------------
    // Metodo que bloquea el evento y llama al metodo revisar para iniciar el proceso de revisión
    @PostMapping("/{eventoId}/bloquear-revisar")
    public EventoSismico bloquearEvento(Long eventoId, Usuario usuarioQueSelecciona, ResultadoRevisionDTO datosInicialesRevision) {
        // --- Paso 1: Bloquear el Evento (para que nadie más lo revise) ---
        EventoSismico evento = repo.findById(eventoId)
            .orElseThrow(() -> new RuntimeException("Evento Sísmico no encontrado con ID: " + eventoId));

        // Solo bloquea si no está ya bloqueado
        if (evento.getEstadoEvento() != EstadoEvento.BLOQUEADO) {
            evento.setEstadoEvento(EstadoEvento.BLOQUEADO);
            //eventoSismicoRepository.save(evento); // Persistir el cambio a estado BLOQUEADO
        } else {
            System.out.println("Evento ID " + eventoId + " ya estaba bloqueado. No se requiere acción adicional.");
        }

        // --- Paso 2: INICIAR el proceso de revisión llamando a revisar() ---
        // Aquí es donde 'bloquearEvento' invoca a 'revisar'.
        EventoSismico eventoRevisado = eventoService.revisar(eventoId, datosInicialesRevision, usuarioQueSelecciona, cambioEstadoService);

        return eventoRevisado; // Devuelve el evento ya revisado
    }
 
    // Metodo nro 34 --------------------------------------------------------------------------------------------------
    // Metodo que buscara los datos sismicos del evento seleccionado
    @GetMapping("/{id}")
    public ResponseEntity<EventoSismicoDTO> buscarDatosSismicos(@PathVariable Long id) {
        try {
            EventoSismico evento = eventoService.getById(id);
            return ResponseEntity.ok(eventoMapper.toDTO(evento));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            // Loggear la excepción para depuración
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // O un mensaje de error personalizado
        }
    }

    // Metodo nro 51 --------------------------------------------------------------------------------------------------
    // Invocacion del caso de uso "Generar Sismograma"
    @PostMapping("/{id}/procesar-evento")
    public ResponseEntity<Void> invocarCU(@PathVariable Long id) {
        sismogramaService.procesarSismograma(id);
        return ResponseEntity.ok().build();
    }

    // Metodo nro 55 --------------------------------------------------------------------------------------------------
    // Metodo que obtendra los datos del evento sismico rechazado seleccionado
    public String tomarSeleccionarRechazarEvento(EventoSismico evento) {
        return "Evento Sismico seleccionado para rechazar: " + evento.getId();
    }

    // Metodo nro 56 --------------------------------------------------------------------------------------------------
    // Metodo que validara los datos ingresados para la accion a realizar sobre el evento sismico seleccionado
     public void validarDatosAccion(EstadoService estadoEventoService, EventoSismico evento) {
        Boolean ambito = estadoEventoService.esAmbitoEventoSismico(evento.getEstadoEvento());

        // Obtener el nombre del estado del evento
        String nombreEstado = null;
        if (evento.getEstadoEvento() != null) {
            nombreEstado = evento.getEstadoEvento().getNombre();
        }
        String estadoRechazado = estadoEventoService.esRechazado(evento.getEstadoEvento());

        // Puedes usar estos valores para validaciones, logs, etc.
        System.out.println("Ámbito del evento: " + ambito);
        System.out.println("Estado seleccionado: " + nombreEstado);
        System.out.println("¿Es rechazado?: " + estadoRechazado);

    }

    // Metodo nro 57 --------------------------------------------------------------------------------------------------
    // Metodo que obtendra la fecha y hora actual del sistema
    public String obtenerFechaHoraActual() {
        // Obtener la fecha y hora actual
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        // Formatear la fecha y hora como una cadena
        return fechaHoraActual.toString();
    }

    // Metodo nro 58 --------------------------------------------------------------------------------------------------
    // Metodo que validara si el estado seleccionado pertenece al ambito de eventos sismicos
    public boolean esAmbitoEventoSismico(EstadoEvento estadoEvento) {
        return estadoEventoService.esAmbitoEventoSismico(estadoEvento);
    }

    // Metodo nro 59 --------------------------------------------------------------------------------------------------
    // Metodo que validara si el estado seleccionado es "Rechazado"
    public String esRechazado(EstadoEvento estadoEvento) {
        return estadoEventoService.esRechazado(estadoEvento);
    }

    // Metodo nro 60 --------------------------------------------------------------------------------------------------
    // Metodo que inicializara el proceso de rechazo del evento sismico seleccionado y llamara al metodo rechazar
     @PutMapping("/{id}/rechazar")
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

    // Metodos utiles para el controlador
    //-----------------------------------------------------------------------------------------------------------

    // 3. Finalizar estado actual y registrar nuevo cambio de estado
    @PostMapping("/{id}/cambiar-estado")
    public ResponseEntity<CambioEstadoDTO> crearCambioEstado(@PathVariable Long id, @RequestParam Long nuevoEstadoId) {
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
       Clasificacion guardada = clasificacionService.clasificar(clasificacion);
       eventoService.obtenerClasificacion(guardada);
       return ResponseEntity.ok(clasificacionMapper.toDTO(guardada));
    }
    
    // 5. Obtener series temporales de un evento
    @GetMapping("/{id}/series-temporales")
    public ResponseEntity<List<SerieTemporalDTO>> obtenerSeries(@PathVariable Long id) {
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

        if (evento == null) {
            return ResponseEntity.notFound().build();
        }

        List<EstacionSismologica> estaciones = evento.getEstacionesSismologicas();
        List<EstacionSismologicaDTO> dtos = estaciones.stream()
            .map(estacionMapper::toDTO)
            .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

}



