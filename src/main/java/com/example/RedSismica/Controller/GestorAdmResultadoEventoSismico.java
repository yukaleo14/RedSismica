package com.example.RedSismica.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.RedSismica.Model.EventoSismico;
import com.example.RedSismica.Model.Sesion;
import com.example.RedSismica.Model.EstadoEvento;

public class GestorAdmResultadoEventoSismico {
    // Atributos
    private List<EventoSismico> eventosSismicos;
    private EventoSismico eventoSeleccionado;
    private Sesion sesionActual;
    private boolean eventoBloqueado;
    private LocalDateTime fechaHoraActual;
    private EstadoEvento estadoBloqueado;
    
    // Constructor
    public GestorAdmResultadoEventoSismico() {
        this.eventosSismicos = new ArrayList<>();
        this.eventoBloqueado = false;
        this.fechaHoraActual = LocalDateTime.now();
    }
    
    // Métodos principales
    
    public void OpcionRegistrarResultRevEventoSismico() {
        // Implementación para registrar resultados de revisión de evento sísmico
        if (ValidarDatosAccion()) {
            BuscarEventosSismicosAuto();
            InvocarCU();
        }
    }
    
    public List<EventoSismico> BuscarEventosSismicosAuto() {
        // Lógica para buscar eventos sísmicos automáticamente
        // Normalmente esto se haría con una conexión a base de datos
        // Aquí devolvemos una lista simulada para el ejemplo
        return eventosSismicos;
    }
    
    public List<EventoSismico> OrdenarEventosPorFechaHora() {
        // Ordena los eventos por fecha y hora (más recientes primero)
        List<EventoSismico> eventosOrdenados = new ArrayList<>(eventosSismicos);
        Collections.sort(eventosOrdenados, new Comparator<EventoSismico>() {
            @Override
            public int compare(EventoSismico e1, EventoSismico e2) {
                return e2.getFechaHora().compareTo(e1.getFechaHora());
            }
        });
        return eventosOrdenados;
    }
    
    public void TomarSeleccionEventoSismico(EventoSismico evento) {
        // Toma la selección del usuario sobre un evento sísmico
        this.eventoSeleccionado = evento;
        BuscarEstadoBloqueado();
        BuscarDatosSismicos();
    }
    
    public boolean BuscarEstadoBloqueado() {
        // Verifica si el evento seleccionado está bloqueado
        if (eventoSeleccionado != null) {
            eventoBloqueado = eventoSeleccionado.getEstado().equals(Estado.BLOQUEADO);
            return eventoBloqueado;
        }
        return false;
    }
    
    public LocalDateTime TomarFechaHoraActual() {
        // Obtiene la fecha y hora actual del sistema
        this.fechaHoraActual = LocalDateTime.now();
        return fechaHoraActual;
    }
    
    public Empleado BuscarEmpleadoLogueado() {
        // Obtiene el empleado actualmente logueado
        if (sesionActual != null) {
            return sesionActual.getEmpleado();
        }
        return null;
    }
    
    public void BloquearEvento() {
        // Bloquea el evento seleccionado
        if (eventoSeleccionado != null) {
            eventoSeleccionado.setEstado(Estado.BLOQUEADO);
            eventoBloqueado = true;
        }
    }
    
    public DatosSismicos BuscarDatosSismicos() {
        // Obtiene los datos sísmicos asociados al evento seleccionado
        if (eventoSeleccionado != null) {
            return eventoSeleccionado.getDatosSismicos();
        }
        return null;
    }
    
    public void InvocarCU() {
        // Invoca el caso de uso correspondiente
        // Aquí iría la lógica para invocar otro caso de uso
        System.out.println("Invocando caso de uso relacionado...");
    }
    
    public void TomarSeleccionRechazarEvento() {
        // Procesa la selección de rechazar un evento
        if (eventoSeleccionado != null && !eventoBloqueado) {
            eventoSeleccionado.setAprobado(false);
            // Aquí podrías agregar más lógica relacionada con el rechazo
        }
    }
    
    public boolean ValidarDatosAccion() {
        // Valida los datos antes de realizar una acción
        Empleado empleado = BuscarEmpleadoLogueado();
        return empleado != null && empleado.tienePermiso("GESTION_EVENTOS");
    }
    
    public LocalDateTime obtenerFechaHoraActual() {
        // Versión alternativa para obtener fecha/hora actual
        return LocalDateTime.now();
    }
    
    public void FinCU() {
        // Finaliza el caso de uso, limpiando variables temporales
        this.eventoSeleccionado = null;
        this.eventoBloqueado = false;
        System.out.println("Caso de uso finalizado correctamente");
    }
    
    // Getters y Setters
    public List<EventoSismico> getEventosSismicos() {
        return eventosSismicos;
    }
    
    public void setEventosSismicos(List<EventoSismico> eventosSismicos) {
        this.eventosSismicos = eventosSismicos;
    }
    
    public EventoSismico getEventoSeleccionado() {
        return eventoSeleccionado;
    }
    
    public Sesion getSesionActual() {
        return sesionActual;
    }
    
    public void setSesionActual(Sesion sesionActual) {
        this.sesionActual = sesionActual;
    }
    
    public boolean isEventoBloqueado() {
        return eventoBloqueado;
    }
}
