package com.example.RedSismica.Model;

import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SerieTemporal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private EventoSismico evento;

    @ManyToOne
    @JoinColumn(name = "estacion_id")
    private EstacionSismologica estacionSismologica;

    @OneToMany
    @JoinColumn(name = "muestra_id")
    private MuestraSismica muestraSismica;


    private LocalDateTime fechaHoraRegistroMuestra;
    private LocalDateTime fechaHoraRegristo;
    private Double frecuenciaMuestreo;
    private String condicionAlarma;

    //Getters
   
    public void getDatos() {
        System.out.println("Fecha y hora de registro de la muestra: " + fechaHoraRegistroMuestra);
        System.out.println("Fecha y hora de registro: " + fechaHoraRegristo);
        System.out.println("Frecuencia de muestreo: " + frecuenciaMuestreo);
        System.out.println("Condición de alarma: " + condicionAlarma);
    }

}
