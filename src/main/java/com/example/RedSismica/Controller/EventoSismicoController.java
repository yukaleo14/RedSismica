package com.example.RedSismica.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RedSismica.Model.EventoSismico;
import com.example.RedSismica.Service.EventoSismicoService;

@RestController
@RequestMapping ("/api/EventoSismico")
public class EventoSismicoController {
    
    private EventoSismicoService eventoSismicoService; 

    @Autowired
    public EventoSismicoController(EventoSismicoService eventoSismicoService) {
        this.eventoSismicoService = eventoSismicoService;
    }

    //Mostrar Evento Sismico en listado ordenado por fecha y hora
    @GetMapping("/ParaSeleccion")
    public ResponseEntity<List<EventoSismico>> ParaSeleccion() {
        List<EventoSismico> eventoSismicos = eventoSismicoService.MostrarEventosSismicosParaSeleccion();
        return ResponseEntity.ok(eventoSismicos);
    }
    //Filtrado por AutoDectado
    //Filtrado por Pendiente en Revision

}

