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

    @ManyToOne
    @JoinColumn(name = "muestra_sismica_id")
    private MuestraSismica muestraSismica;

    //getters
    public String getValor() {
        return valor;
    }


}
