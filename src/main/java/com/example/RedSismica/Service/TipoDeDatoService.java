package com.example.RedSismica.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RedSismica.Model.TipoDeDato;
import com.example.RedSismica.Repository.TipoDeDatoRepository;

@Service
public class TipoDeDatoService {

     @Autowired
    private TipoDeDatoRepository tipoDeDatoRepository;

    public String esTuDenominacion(Long id, String denominacion) {
        TipoDeDato tipoDeDato = tipoDeDatoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("TipoDeDato no encontrado"));
        if (tipoDeDato.getDenominacion().equals(denominacion)) {
            return "La denominación es correcta";
        } else {
            return "La denominación no es correcta";
        }
    }

    public String getDenominacion(Long id) {
        TipoDeDato tipoDeDato = tipoDeDatoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("TipoDeDato no encontrado"));
        return tipoDeDato.getDenominacion();
    }
    
}
