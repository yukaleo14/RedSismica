package com.example.RedSismica.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RedSismica.Model.EstadoEvento;

@Repository
public interface EstadoEventoRepository extends JpaRepository<EstadoEvento, Long> {}
