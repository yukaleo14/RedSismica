package com.example.RedSismica.Model;

import java.sql.Date;

import com.example.RedSismica.Usuario.Usuario;

import jakarta.persistence.Entity;
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
public class Sesion {

    private Date fechaHoraInicio;
    private Date fechaHoraFin;

    @OneToOne
    private Usuario usuario;
}
