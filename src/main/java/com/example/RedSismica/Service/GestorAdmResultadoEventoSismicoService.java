package com.example.RedSismica.Service;

import com.example.RedSismica.Model.*;
import com.example.RedSismica.Repository.EventoSismicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;



@Service
@Transactional
public class GestorAdmResultadoEventoSismicoService {

    private final EventoSismicoRepository eventoSismicoRepository;
    private final SesionService sesionService;
    private final EmpleadoService empleadoService;

    private EventoSismico eventoSeleccionado;
    private boolean eventoBloqueado;
    private LocalDateTime fechaHoraActual;

    @Autowired
    public GestorAdmResultadoEventoSismicoService(EventoSismicoRepository eventoSismicoRepository,
                                                SesionService sesionService,
                                                EmpleadoService empleadoService) {
        this.eventoSismicoRepository = eventoSismicoRepository;
        this.sesionService = sesionService;
        this.empleadoService = empleadoService;
    }

    public void registrarResultadoRevisionEventoSismico() {
        if (validarDatosAccion()) {
            buscarEventosSismicosAuto();
            invocarCasoUso();
        }
    }

    public List<EventoSismico> buscarEventosSismicosAuto() {
        return eventoSismicoRepository.findAll();
    }

    public List<EventoSismico> ordenarEventosPorFechaHora() {
        List<EventoSismico> eventos = eventoSismicoRepository.findAll();
        eventos.sort(Comparator.comparing(EventoSismico::getFechaHoraOcurrencia).reversed());
        return eventos;
    }

    public void tomarSeleccionEventoSismico(Long eventoId) {
        Optional<EventoSismico> eventoOpt = eventoSismicoRepository.findById(eventoId);
        if (eventoOpt.isPresent()) {
            this.eventoSeleccionado = eventoOpt.get();
            buscarEstadoBloqueado();
            buscarDatosSismicos();
        }
    }

    public boolean buscarEstadoBloqueado() {
        if (eventoSeleccionado != null) {
            eventoBloqueado = eventoSeleccionado.getEstadoEvento() == Estado.BLOQUEADO;
            return eventoBloqueado;
        }
        return false;
    }

    public LocalDateTime tomarFechaHoraActual() {
        this.fechaHoraActual = LocalDateTime.now();
        return fechaHoraActual;
    }

    public Empleado buscarEmpleadoLogueado() {
        return sesionService.getEmpleadoLogueado();
    }

    public void bloquearEvento() {
        if (eventoSeleccionado != null) {
            eventoSeleccionado.setEstado(Estado.BLOQUEADO);
            eventoSismicoRepository.save(eventoSeleccionado);
            eventoBloqueado = true;
        }
    }

    public DatosSismicos buscarDatosSismicos() {
        if (eventoSeleccionado != null) {
            return eventoSeleccionado.getDatosSismicos();
        }
        return null;
    }

    public void invocarCasoUso() {
        // Lógica para invocar otro caso de uso
    }

    public void tomarSeleccionRechazarEvento() {
        if (eventoSeleccionado != null && !eventoBloqueado) {
            eventoSeleccionado.setAprobado(false);
            eventoSismicoRepository.save(eventoSeleccionado);
        }
    }

    public boolean validarDatosAccion() {
        Empleado empleado = buscarEmpleadoLogueado();
        return empleado != null && empleadoService.tienePermiso(empleado, "GESTION_EVENTOS");
    }

    public LocalDateTime obtenerFechaHoraActual() {
        return LocalDateTime.now();
    }

    public void finalizarCasoUso() {
        this.eventoSeleccionado = null;
        this.eventoBloqueado = false;
    }

    // Getters para estado actual
    public EventoSismico getEventoSeleccionado() {
        return eventoSeleccionado;
    }

    public boolean isEventoBloqueado() {
        return eventoBloqueado;
    }
}