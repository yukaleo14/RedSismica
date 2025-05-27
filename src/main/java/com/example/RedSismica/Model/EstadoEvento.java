package com.example.RedSismica.Model;

import java.time.LocalDateTime;

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
    
    public static LocalDateTime esAutodetectado;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ambito;
    private String nombre;

    //Va en Service
    public boolean esAutoDetectado() {
        if (nombre.equals("AutoDetectado")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean esPendRevision() {
        if (nombre.equals("PendienteRevision")) {
            return true;
        } else {
            return false;
        }
    }
    //verificamos si el estado es ambito de evento sismico
    public boolean esAmbitoEventoSismico() {
        if (ambito.equals("EventoSismico")) {
            return true;
        } else {
            return false;
        }
    }


    public boolean esBloqueadoEnPeticion() {
        if (nombre.equals("BloqueadoEnPeticion")) {
            return true;
        } else {
            return false;
        }
    }

    public String esRechazado() {
        if (nombre.equals("Rechazado")) {
            return "El evento es rechazado";
        } else {
            return "El evento no es rechazado";
        }
    }



}
