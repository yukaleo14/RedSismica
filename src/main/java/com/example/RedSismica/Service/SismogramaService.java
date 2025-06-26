package com.example.RedSismica.Service;

import org.springframework.stereotype.Service;

@Service
public class SismogramaService {
    public void procesarSismograma(Long eventoId) {
        System.out.println("Procesando sismograma para el evento sísmico con ID: " + eventoId);
    }
}
