package com.example.RedSismica.Model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GestorAdmResultadoEventoSismico {
    private List<EventoSismico> eventosSismicos;
    private EventoSismico eventoSeleccionado;
    private Sesion sesionActual;
    private boolean eventoBloqueado;
    private LocalDateTime fechaHoraActual;
    private EstadoEvento estadoBloqueado;
    

}
