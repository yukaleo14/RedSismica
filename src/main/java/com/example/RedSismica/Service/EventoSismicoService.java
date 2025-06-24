package com.example.RedSismica.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RedSismica.Model.Clasificacion;
import com.example.RedSismica.Model.EstadoEvento;
import com.example.RedSismica.Model.EventoSismico;
import com.example.RedSismica.Model.Sesion;
import com.example.RedSismica.Repository.EventoSismicoRepository;
import com.example.RedSismica.Usuario.Usuario;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EventoSismicoService {
    
    @Autowired private EventoSismicoRepository repo;
    private final EventoSismicoRepository eventoSismicoRepository;
    private final CambioEstadoService cambioEstadoService; // Declara la dependencia


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

    public String buscarEmpleadoLogueado(SesionService sesion) {
        return SesionService.getUsuarioLogueado(sesion);
    }

    public EventoSismico bloquearEvento(Long eventoId, Usuario usuarioQueSelecciona, ResultadoRevisionDTO datosInicialesRevision) {
            // --- Paso 1: Bloquear el Evento (para que nadie más lo revise) ---
            EventoSismico evento = eventoSismicoRepository.findById(eventoId)
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
    EstadoEvento nuevoEstadoEvento;

    if ("RECHAZADO".equalsIgnoreCase(accionFinal)) {
        nuevoEstadoEvento = EstadoEvento.RECHAZADO;
    } else if ("CONFIRMADO".equalsIgnoreCase(accionFinal)) {
        nuevoEstadoEvento = EstadoEvento.CONFIRMADO;
    } else {
        throw new IllegalArgumentException("Acción de revisión final no reconocida: " + accionFinal);
    }

    // --- Aquí es donde se invoca la lógica del CambioEstadoService ---

    // Lógica para finalizar el CambioEstado anterior, si aplica.
    // Esto implicaría buscar el CambioEstado actual del evento.
    // Aunque el diagrama de secuencia muestra "esActual()" y "setFechaHoraFin()",
    // en la práctica primero necesitas obtener el objeto CambioEstado actual.
    // Asumiendo que tendrías un método para encontrar el 'CambioEstado' activo de un evento:
    // CambioEstado cambioEstadoPrevio = cambioEstadoService.buscarCambioEstadoActualParaEvento(eventoId);
    // if (cambioEstadoPrevio != null) {
    //     cambioEstadoService.finalizarCambioEstadoActual(cambioEstadoPrevio); // Invoca el método en CambioEstadoService
    // }


    EventoSismico.setEstadoEvento(nuevoEstadoEvento); // Actualiza el estado del Evento Sísmico
    EventoSismico.setFechaHoraRevision(LocalDateTime.now());
    EventoSismico.setUsuarioRevisor(usuarioResponsable);

    EventoSismico eventoConTodosLosCambios = eventoSismicoRepository.save(evento);


    // Crear un nuevo registro de CambioEstado para el nuevo estado del evento.
    // Esto corresponde a los pasos 31, 32 y 33 del diagrama de secuencia:
    // 31- crearCambioEstado()
    // 32- new() de CambioEstado
    // 33- setEstado()
    cambioEstadoService.crearNuevoCambioEstado(eventoId, nuevoEstadoEvento.name(), LocalDateTime.now()); // Invoca el método en CambioEstadoService


    return eventoConTodosLosCambios;
    }

    //---------------------------------------------------------------------
    public void asociarClasificacion(Long id, Clasificacion guardada) {
        throw new UnsupportedOperationException("Unimplemented method 'asociarClasificacion'");
    }

    public EventoSismico getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento con ID " + id + " no encontrado"));
    }

    public EventoSismico revisar(){

    }
}
