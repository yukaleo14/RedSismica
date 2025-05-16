package com.example.RedSismica.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

public class DetalleMuestraSismica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String valor;

    @OneToMany
    private TipoDeDato tipoDeDato;


    //getters
    public String getValor() {
        return valor;
    }


}
