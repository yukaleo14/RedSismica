package com.example.RedSismica.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstadoEvento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ambito;
    private String nombre;

    public String esAutoDetectado() {
        if (nombre.equals("AutoDetectado")) {
            return "El evento es auto detectado";
        } else {
            return "El evento no es auto detectado";
        }
    }

    public String esPendRevision() {
        if (nombre.equals("PendienteRevision")) {
            return "El evento es pendiente de revision";
        } else {
            return "El evento no es pendiente de revision";
        }
    }


}
