package com.example.RedSismica.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RedSismica.Model.EstacionSismologica;
import com.example.RedSismica.Model.EventoSismico;

@Repository
public interface EstacionSismologicaRepository extends JpaRepository<EstacionSismologica, Long> {
    List<EstacionSismologica> findByEventoSismico(EventoSismico evento);
}   
