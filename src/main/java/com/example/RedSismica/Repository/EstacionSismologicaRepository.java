package com.example.RedSismica.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RedSismica.Model.EstacionSismologica;

@Repository
public interface EstacionSismologicaRepository extends JpaRepository<EstacionSismologica, Long> {
}
