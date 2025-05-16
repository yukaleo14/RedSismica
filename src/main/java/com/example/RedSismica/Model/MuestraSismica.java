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

@AllArgsConstructor
@NoArgsConstructor
public class MuestraSismica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime fechaHoraMuestra;

    @OneToMany
    @JoinColumn(name = "detalle_muestra_id")
    private DetalleMuestraSismica detalleMuestraSismica;

    public void getDatos() {
        System.out.println("Fecha y hora de la muestra: " + fechaHoraMuestra);
    }
    
}
