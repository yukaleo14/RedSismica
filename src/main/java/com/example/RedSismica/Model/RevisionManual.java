package com.example.RedSismica.Model;

import java.time.LocalDateTime;

import com.example.RedSismica.Usuario.Usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class RevisionManual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private EventoSismico evento;

    @ManyToOne
    private Usuario revisor;

    private LocalDateTime fechaRevision;
    private String observacion;
    private String resultadoRevision; //Aprovado, Rechazado, Derivado

}
