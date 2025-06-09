package com.example.RedSismica.Model;

import java.time.LocalDateTime;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class MuestraSismica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime fechaHoraMuestra;
    private float velocidad;
    private float frecuencia;
    private float longitud;

    @OneToMany
    @JoinColumn(name = "detalle_muestra_id")
    private DetalleMuestraSismica detalleMuestraSismica;

    public void getDatos() {
        System.out.println("Fecha y hora de la muestra: " + fechaHoraMuestra);
    }
    
}
