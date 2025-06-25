package com.example.RedSismica.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleMuestraSismica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String valor;

    @ManyToOne
    @JoinColumn(name = "tipo_de_dato_id")
    private TipoDeDato tipoDeDato;

    // AÑADE ESTA RELACIÓN: Un DetalleMuestraSismica pertenece a UNA MuestraSismica
    @ManyToOne
    @JoinColumn(name = "muestra_sismica_id") // Nombre de la columna FK en tu tabla DetalleMuestraSismica
    private MuestraSismica muestraSismica; // <-- Este es el campo que Spring Data busca


    public String getDatos(TipoDeDato tipoDeDato) {
        if (this.tipoDeDato.equals(tipoDeDato)) {
            return this.valor;
        } else {
            return "Tipo de dato no coincide";
        }   
    }
}
