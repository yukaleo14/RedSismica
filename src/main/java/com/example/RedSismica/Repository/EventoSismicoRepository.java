package com.example.RedSismica.Repository;


//import com.example.RedSismica.Model.EstadoEvento;
import com.example.RedSismica.Model.EventoSismico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoSismicoRepository extends JpaRepository<EventoSismico, Long> {
 //Encontrar Listados de Obejetos dentro de Evento Sismico y ordenar por fecha y hora de forma decendente

    // EventoSismicoRepository.java
    List<EventoSismico> findByAutoDetectadoTrueOrPendienteRevisionTrue();

    List<EventoSismico> findByAutoDetectadoTrue();
    List<EventoSismico> findByPendienteRevisionTrue();
    List<EventoSismico> OrderByFechaHoraOcurrenciaDesc();

    //List<EventoSismico> findByEstadoEvento(String string); //agregado para buscar PENDIENTE en evento sismico service

    //EventoSismico saveAll(EstadoEvento nuevoEstadoEvento);

}