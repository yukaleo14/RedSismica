package com.example.RedSismica.Model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

    @OneToOne(cascade = CascadeType.ALL) 
    @JoinColumn(name = "detalle_muestra_id", unique = true)
    private DetalleMuestraSismica detalleMuestraSismica;

    public void getDatos() {
        System.out.println("Fecha y hora de la muestra: " + fechaHoraMuestra);
    }
    
}
