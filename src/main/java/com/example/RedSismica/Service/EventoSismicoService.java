package com.example.RedSismica.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.RedSismica.Model.EventoSismico;
import com.example.RedSismica.Repository.EventoSismicoRepository;

@Service
public class EventoSismicoService {
    
    private EventoSismicoRepository eventosismicorepo;

     public EventoSismicoService(EventoSismicoRepository eventosismicorepo) {
        this.eventosismicorepo = eventosismicorepo;
    }

    public List<EventoSismico> MostrarEventosSismicosParaSeleccion() {
        return eventosismicorepo.findByOrderByFechaHoraOcuerenciaDesc();
    }
}
