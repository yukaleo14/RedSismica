package com.example.RedSismica.Service;

import java.sql.Date;
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

import jakarta.persistence.EntityNotFoundException;

@Service
public class EventoSismicoService {
    
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
