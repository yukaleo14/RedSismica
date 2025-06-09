package com.example.RedSismica.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RedSismica.Model.MuestraSismica;
import com.example.RedSismica.Model.SerieTemporal;

@Repository
public interface MuestraSismicaRepository extends JpaRepository<MuestraSismica, Long> {
    List<MuestraSismica> findBySerieTemporal(SerieTemporal serie);
}

