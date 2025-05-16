package com.example.RedSismica.Repository;


import com.example.RedSismica.Model.EventoSismico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoSismicoRepository extends JpaRepository<EventoSismico, Long> {
}