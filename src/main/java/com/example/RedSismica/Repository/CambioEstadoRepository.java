package com.example.RedSismica.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RedSismica.Model.CambioEstado;
import com.example.RedSismica.Model.EventoSismico;

@Repository
public interface CambioEstadoRepository extends JpaRepository<CambioEstado, Long> {
    Optional<CambioEstado> findByEventoSismicoAndActualTrue(EventoSismico evento);
}
    
