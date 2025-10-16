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
    @Autowired private EstadoService estadoEventoService;

    public List<EventoSismico> buscarEventosSismicosAutoDetectado() {
        return repo.findByAutoDetectadoTrueOrPendienteRevisionTrue();
    }

    // Metodo nro 5 --------------------------------------------------------------------------------------------------
    // Metodo para buscar el estado del evento en la entidad EstadoEventoService
    public List<EventoSismico> esAutoDetectado() {
        return estadoEventoService.esAutoDetectado();
    }

    // Metodo nro 7 --------------------------------------------------------------------------------------------------
    // Metodo para buscar el estado del evento en la entidad EstadoEventoService
    public List<EventoSismico> esPendienteRevision() {
        return estadoEventoService.esPendienteRevision();
    }

    // Metodos nro 10, 11, 12, 13, 14 y 15 ---------------------------------------------------------------------------
    // Definicion de funcionalidad de los metodos que se utilizaran en el metodo nro 9
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

    // Metodo nro 9 --------------------------------------------------------------------------------------------------
    // Metodo para obtener cada dato principal del evento sismico
    // Incluira los metodos nro 10, 11, 12, 13, 14 y 15 mostrados en el diagrama de secuencia
    public String getDatosPrincipales(EventoSismico evento) {
        // Implement logic to return main data as a String, or remove this method if not needed
        return "Latitud Epicentro: " + getLatitudEpicentro(evento) +
               ", Longitud Epicentro: " + getLongitudEpicentro(evento) +
               ", Latitud Hipocentro: " + getLatitudHipocentro(evento) +
               ", Longitud Hipocentro: " + getLongitudHipocentro(evento) +
               ", Magnitud: " + getMagnitud(evento);
    }

    
    // Metodo nro 28 --------------------------------------------------------------------------------------------------
    // Metodo que revisa el evento sismico y actualiza su estado
    public EventoSismico revisar(Long eventoId, ResultadoRevisionDTO datosDeLaRevision, Usuario usuarioResponsable, CambioEstadoService cambioEstadoService) {
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

        // Metodo nro 29 --------------------------------------------------------------------------------------------------
        // Registrar el cambio de estado
         CambioEstado cambioActual = cambioEstadoService.esActual(evento.getEstadoEvento()) ? cambioEstadoService.getCambioEstadoActual(evento) : null;
        if (cambioActual != null) {
            cambioEstadoService.finalizarCambio(cambioActual);
        }
        // Metodos nro 30, 31 y 32 ----------------------------------------------------------------------------------------------
        // Crear un nuevo cambio de estado y asignarlo al evento
        CambioEstado nuevoCambio = new CambioEstado();
        nuevoCambio.setFechaHoraInicio(LocalDateTime.now());
        nuevoCambio.setEstadoEvento(estadoEventoService.getById(eventoId));
        cambioEstadoService.crearCambioEstado(nuevoCambio);

        // Metodo nro 33 --------------------------------------------------------------------------------------------------
        estadoEventoService.setEstado(nuevoEstadoEvento);
        EventoSismico eventoConTodosLosCambios = repo.save(evento);

        return eventoConTodosLosCambios;
    }

    // Metodos nro 36, 37 y 38 ----------------------------------------------------------------------------------------------
    // Definicion de funcionalidad de los metodos que se utilizaran en el metodo
    public String getAlcance(EventoSismico evento) {
       return evento.getAlcance();
    }
    public String getOrigen(EventoSismico evento) {
        return evento.getOrigen(); //estaba mal escrito, antes estaba getOrigenGeneracion y en la secuencia getOrigen
    }
    public String obtenerClasificacion(Clasificacion clasificacion) {
        return clasificacion.getNombre();
    }
    
    // Metodo nro 35 --------------------------------------------------------------------------------------------------
    // Metodo que obtendra los datos del evento sismico seleccionado
    public String obtenerDatos(EventoSismico evento) {
        return "Alcance: " + getAlcance(evento) +
               ", Origen: " + getOrigen(evento) +
               ", Clasificación: " + obtenerClasificacion(evento.getClasificacion());
    }

    // Metodo nro 40 --------------------------------------------------------------------------------------------------
    // Metodo que obtendra las series temporales del evento sismico seleccionado
    public String obtenerSeriesTemporales(SerieTemporalService serieTemporalService, Long eventoId, String tipoMuestra) {

        serieTemporalService.getMuestras(eventoId, tipoMuestra);
        return "Series temporales del evento sísmico: operación completada";
    }   

    // Metodo nro 50 --------------------------------------------------------------------------------------------------
    // Metodo que clasificara la informacion del evento sismico seleccionado
    public String clasificarInformacion(Clasificacion clasificacion) {
        return "Clasificación del evento sísmico: " + clasificacion.getNombre();
    }

    
    // Metodo nro 61 --------------------------------------------------------------------------------------------------
    // Metodo de inicilizacion de cambio de estado a rechazado
    public EventoSismico rechazar(Long eventoId, EstadoService estadoEventoService, CambioEstadoService cambioEstadoService, Usuario usuarioService) {

        EventoSismico evento = repo.findById(eventoId)
            .orElseThrow(() -> new RuntimeException("Evento Sísmico no encontrado con ID: " + eventoId));

        // Metodos nro 62, 63 y 64 ----------------------------------------------------------------------------------------------
        // Metodos utilizados para cambio de estado del evento sismico
        CambioEstado cambioActual = cambioEstadoService.esActual(evento.getEstadoEvento()) ? cambioEstadoService.getCambioEstadoActual(evento) : null;
        if (cambioActual != null) {
            cambioEstadoService.finalizarCambio(cambioActual);
        }
        CambioEstado actualCambioEstado = new CambioEstado();
        actualCambioEstado.setEventoSismico(evento);
        actualCambioEstado.setFechaHoraInicio(LocalDateTime.now());
        actualCambioEstado.setEstadoEvento(estadoEventoService.getById(eventoId));

        cambioEstadoService.crearCambioEstado(actualCambioEstado);

        return repo.save(evento);
    }

    // Metodo 66 --------------------------------------------------------------------------------------------------
    // Metodo que actualizara el estado del evento sismico seleccionado al estado pasado por parametro
    public String setEstado(EventoSismico evento, EstadoEvento nuevoEstado) {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El nuevo estado no puede ser nulo");
        }   
        evento.setEstadoEvento(nuevoEstado);
        repo.save(evento);
        return "Estado actualizado a: " + nuevoEstado.getNombre();
    }

    

    //El evento sismico no puede volver a bloqueado si ya fue confirmado o rechazado
    public String bloquearEventoSismico(EventoSismico evento) {
        if (evento.getEstadoEvento().getNombre().equals("Confirmado") || evento.getEstadoEvento().getNombre().equals("Rechazado")) {
            throw new IllegalStateException("El evento sismico no puede volver a bloqueado si ya fue confirmado o rechazado");
        }
        return "El evento sísmico ha sido bloqueado correctamente";
    }

    public EventoSismico getById(Long id) {
    return repo.findById(id)
            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Evento con ID " + id + " no encontrado"));
    }
}
