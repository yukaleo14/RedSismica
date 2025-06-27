package com.example.RedSismica.Controller;

import java.sql.Date;
import java.time.LocalDateTime;
//import java.util.ArrayList;
import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.example.RedSismica.DTO.CambioEstadoDTO;
//import com.example.RedSismica.DTO.ClasificacionDTO;
//import com.example.RedSismica.DTO.EstacionSismologicaDTO;
//import com.example.RedSismica.DTO.EventoSismicoDTO;
//import com.example.RedSismica.DTO.MuestraSismicaDTO;
//import com.example.RedSismica.DTO.SerieTemporalDTO;
//import com.example.RedSismica.Mapper.CambioEstadoMapper;
//import com.example.RedSismica.Mapper.ClasificacionMapper;
//import com.example.RedSismica.Mapper.EstacionSismologicaMapper;
//import com.example.RedSismica.Mapper.EventoSismicoMapper;
//import com.example.RedSismica.Mapper.MuestraSismicaMapper;
//import com.example.RedSismica.Mapper.SerieTemporalMapper;
import com.example.RedSismica.Model.CambioEstado;
import com.example.RedSismica.Model.Clasificacion;
//import com.example.RedSismica.Model.EstacionSismologica;
import com.example.RedSismica.Model.EstadoEvento;
import com.example.RedSismica.Model.EventoSismico;
//import com.example.RedSismica.Model.SerieTemporal;
import com.example.RedSismica.Repository.EventoSismicoRepository;
import com.example.RedSismica.Service.CambioEstadoService;
//import com.example.RedSismica.Service.ClasificacionService;
//import com.example.RedSismica.Service.EstacionSismologicaService;
import com.example.RedSismica.Service.EstadoEventoService;
//import com.example.RedSismica.Service.EventoSismicoService;
//import com.example.RedSismica.Service.MuestraSismicaService;
import com.example.RedSismica.Service.ResultadoRevisionDTO;
//import com.example.RedSismica.Service.SerieTemporalService;
import com.example.RedSismica.Service.SesionService;
//import com.example.RedSismica.Service.SismogramaService;
import com.example.RedSismica.Usuario.Usuario;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class GestorAdmResultadoEventoSismico {

    //private final EventoSismicoService eventoService;
    //private final EventoSismicoMapper eventoMapper;
    //private final CambioEstadoService cambioEstadoService;
    //private final CambioEstadoMapper cambioEstadoMapper;
    //private final ClasificacionService clasificacionService;
    //private final ClasificacionMapper clasificacionMapper;
    //private final EstadoEventoService estadoEventoService;
    //private final SerieTemporalService serieTemporalService;
    //private final SerieTemporalMapper serieTemporalMapper;
    //private final MuestraSismicaService muestraSismicaService;
    //private final MuestraSismicaMapper muestraSismicaMapper;
    //private final EstacionSismologicaService estacionService;
    //private final EstacionSismologicaMapper estacionMapper;
    //private final EventoSismicoRepository eventoSismicoRepository;
    //private final SismogramaService sismogramaService;

    @Autowired private EventoSismicoRepository repo;

    public List<EventoSismico> buscarEventosSismicosAutoDetectado() {
    return repo.findByAutoDetectadoTrueOrPendienteRevisionTrue();
    }

    public boolean esAutoDetectado(EventoSismico evento) {
        return evento.esAutoDetectado();
    }

    public boolean esPendienteRevision(EventoSismico evento) {
        return evento.esPendienteRevision();
    }

    public String getDatosPrincipales(EventoSismico evento) {
        return evento.getDatosPrincipales();
    }

    public LocalDateTime getFechaHoraOcurrencia(EventoSismico evento) {
        return evento.getFechaHoraOcurrencia();
    }

    public double getLatitudEpicentro(EventoSismico evento) {
        return evento.getLatitudEpicentro();
    }

    public double getLongitudEpicentro(EventoSismico evento) {
        return evento.getLongitudEpicentro();
    }

    public double getLatitudHipocentro(EventoSismico evento) {
        return evento.getLatitudHipocentro();
    }

    public double getLongitudHipocentro(EventoSismico evento) {
        return evento.getLongitudHipocentro();
    }

    public double getMagnitud(EventoSismico evento) {
        return evento.getMagnitud();
    }

    public  List<EventoSismico> ordenarEventosPorFechaHora() {
        return repo.OrderByFechaHoraOcurrenciaDesc();
    }

    public Date getFechaHoraActual () {
        return Date.valueOf(LocalDateTime.now().toLocalDate());
    }

    public String buscarEmpleadoLogueado(Usuario usuario) {
        return SesionService.getUsuarioLogueado(usuario);
    }

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
        EventoSismico eventoRevisado = revisar(eventoId, datosInicialesRevision, usuarioQueSelecciona);

        return eventoRevisado; // Devuelve el evento ya revisado
}

    public EventoSismico revisar(Long eventoId, ResultadoRevisionDTO datosDeLaRevision, Usuario usuarioResponsable) {
        // ... Código previo para cargar y validar el evento ...

        // Actualiza el estado del evento sísmico.
        String accionFinal = datosDeLaRevision.getComentariosAdicionales();
        // Cargar el evento sísmico a revisar
        EventoSismico evento = repo.findById(eventoId)
            .orElseThrow(() -> new RuntimeException("Evento Sísmico no encontrado con ID: " + eventoId));

        EstadoEvento nuevoEstadoEvento = new EstadoEvento();
        if ("Rechazado".equalsIgnoreCase(accionFinal)) {
            nuevoEstadoEvento.setNombre(accionFinal);
        } else if ("Confirmado".equalsIgnoreCase(accionFinal)) {
            nuevoEstadoEvento.setNombre(accionFinal);
        } else {
            throw new IllegalArgumentException("Acción de revisión final no reconocida: " + accionFinal);
        }

        evento.setEstadoEvento(nuevoEstadoEvento);
        EventoSismico eventoConTodosLosCambios = repo.save(evento);

        return eventoConTodosLosCambios;
    }

    public String getAlcance(EventoSismico evento) {
        return evento.getAlcance();
    }

    public String getOrigenGeneracion(EventoSismico evento) {
        return evento.getOrigenGeneracion();
    }


    public String obtenerClasificacion(Clasificacion clasificacion) {
        return clasificacion.getNombre();
    }

    public void validarDatosAccion(EstadoEventoService estadoEventoService, EventoSismico evento) {
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

    public EventoSismico rechazar(Long eventoId, EstadoEventoService estadoEventoService, CambioEstadoService cambioEstadoService, Usuario usuarioService) {

        EventoSismico evento = repo.findById(eventoId)
            .orElseThrow(() -> new RuntimeException("Evento Sísmico no encontrado con ID: " + eventoId));

        CambioEstado cambioActual = cambioEstadoService.esActual(evento.getEstadoEvento()) ? cambioEstadoService.getCambioEstadoActual(evento) : null;
        if (cambioActual != null) {
            cambioEstadoService.finalizarCambio(cambioActual);
        }
        CambioEstado nuevoCambio = new CambioEstado();
        nuevoCambio.setEventoSismico(evento);
        nuevoCambio.setFechaHoraInicio(LocalDateTime.now());
        nuevoCambio.setEstadoEvento(estadoEventoService.getById(eventoId));

        cambioEstadoService.crearCambioEstado(nuevoCambio);

        return repo.save(evento);
    }

    public String setEstado(EventoSismico evento, EstadoEvento nuevoEstado) {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El nuevo estado no puede ser nulo");
        }   
        evento.setEstadoEvento(nuevoEstado);
        repo.save(evento);
        return "Estado actualizado a: " + nuevoEstado.getNombre();
    }


    public EventoSismico getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento con ID " + id + " no encontrado"));
    }

}



