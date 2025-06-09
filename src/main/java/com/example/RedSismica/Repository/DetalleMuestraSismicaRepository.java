package com.example.RedSismica.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RedSismica.Model.DetalleMuestraSismica;
import com.example.RedSismica.Model.MuestraSismica;

@Repository
public interface DetalleMuestraSismicaRepository extends JpaRepository<DetalleMuestraSismica, Long> {
    List<DetalleMuestraSismica> findByMuestraSismica(MuestraSismica muestra);
}