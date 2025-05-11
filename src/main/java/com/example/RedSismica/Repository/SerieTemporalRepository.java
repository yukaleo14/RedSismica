package com.example.RedSismica.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RedSismica.Model.SerieTemporal;

@Repository
public interface SerieTemporalRepository extends JpaRepository<SerieTemporal, Long> {
    List<SerieTemporal> findByEvento_IdOrderByInstanteTiempo(Long eventoId);
}
