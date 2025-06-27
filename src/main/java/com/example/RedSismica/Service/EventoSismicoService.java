package com.example.RedSismica.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RedSismica.Model.CambioEstado;
import com.example.RedSismica.Model.Clasificacion;
import com.example.RedSismica.Model.EstadoEvento;
import com.example.RedSismica.Model.EventoSismico;
import com.example.RedSismica.Repository.EventoSismicoRepository;
import com.example.RedSismica.Usuario.Usuario;


@Service
public class EventoSismicoService {

    @Autowired private EventoSismicoRepository repo;
    @Autowired private EstadoEventoService estadoEventoService;

    public List<EventoSismico> buscarEventosSismicosAutoDetectado() {
        return repo.findByAutoDetectadoTrueOrPendienteRevisionTrue();
    }

    public List<EventoSismico> esAutoDetectado() {
        return estadoEventoService.esAutoDetectado();
    }

    public List<EventoSismico> esPendienteRevision() {
        return estadoEventoService.esPendienteRevision();
    }

    public String getDatosPrincipales(EventoSismico evento) {
        // Implement logic to return main data as a String, or remove this method if not needed
        return "Latitud Epicentro: " + getLatitudEpicentro(evento) +
               ", Longitud Epicentro: " + getLongitudEpicentro(evento) +
               ", Latitud Hipocentro: " + getLatitudHipocentro(evento) +
               ", Longitud Hipocentro: " + getLongitudHipocentro(evento) +
               ", Magnitud: " + getMagnitud(evento);
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

    public LocalDateTime getFechaHoraOcurrencia(EventoSismico evento) {
        return evento.getFechaHoraOcurrencia();
    }

    public EventoSismico revisar(Long eventoId, ResultadoRevisionDTO datosDeLaRevision, Usuario usuarioResponsable, CambioEstadoService cambioEstadoService) {
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

         CambioEstado cambioActual = cambioEstadoService.esActual(evento.getEstadoEvento()) ? cambioEstadoService.getCambioEstadoActual(evento) : null;
        if (cambioActual != null) {
            cambioEstadoService.finalizarCambio(cambioActual);
        }
        CambioEstado nuevoCambio = new CambioEstado();
        nuevoCambio.setFechaHoraInicio(LocalDateTime.now());
        nuevoCambio.setEstadoEvento(estadoEventoService.getById(eventoId));

        estadoEventoService.setEstado(nuevoEstadoEvento);
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

    public String clasificarInformacion(Clasificacion clasificacion) {
        // Implement logic to classify information, or remove this method if not needed
        return clasificacion.getNombre();
    }

    public EventoSismico getById(Long id) {
    return repo.findById(id)
            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Evento con ID " + id + " no encontrado"));
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

}
