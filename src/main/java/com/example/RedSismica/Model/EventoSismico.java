package com.example.RedSismica.Model;

import java.time.LocalDateTime;

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
public class EventoSismico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime fechaHoraOcurrencia;
    private LocalDateTime fechaHoraFin;
    private Double latitudHipocentro;
    private Double latitudEpicentro;
    private Double longitudEpicentro;
    private Double longitudHipocentro;
    private Double profundidad;
    private Double magnitud;
    private String alcance;
    private String origenGeneracion;

    @ManyToOne
    private EstadoEvento estadoEvento;

    //Getter
    public LocalDateTime getfechaHoraOcurrencia() {
        return fechaHoraOcurrencia.getNano();
    }
    public LocalDateTime getfechaHoraFin() {
        return fechaHoraFin.getNano();
    }
    public Double getLatitudHipocentro() {
        return latitudHipocentro.longValue();
    }
    public Double getLatitudEpicentro() {
        return latitudEpicentro.longValue();
    }
    public Double getLongitudEpicentro() {
        return longitudEpicentro.longValue();
    }
    public Double getLongitudHipocentro() {
        return longitudHipocentro.longValue();
    }
    public Double getProfundidad() {
        return profundidad.longValue();
    }
    public Double getMagnitud() {
        return magnitud.longValue();
    }
    public String getAlcance() {
        return alcance;
    }
    public String getOrigenGeneracion() {
        return origenGeneracion;
    }
    public EstadoEvento getEstadoEvento() {
        return estadoEvento;
    }
    
    public void buscarEventoSismico(){

    }
    public boolean esAutodetectado(){
        return true;
    }
    public boolean esPendienteDeRevision(){
        return true;
    }
    public void getDatosPrincipales(){
        getfechaHoraOcurrencia();
        getfechaHoraFin();
        getLatitudHipocentro()
        getLatitudEpicentro();
        getLongitudEpicentro();
        getLongitudHipocentro();
        getProfundidad();
        getMagnitud();
        getAlcance();
        getOrigenGeneracion();
    }
    public void getDatosGenerales(){
        getDatosPrincipales();
        getEstadoEvento();
    }
    public void ordenarEventoSismico(){
        // Ordenar por fechaHoraOcurrencia
        // Ordenar por magnitud
        // Ordenar por profundidad
        // Ordenar por latitudHipocentro
        // Ordenar por latitudEpicentro
        // Ordenar por longitudHipocentro
        // Ordenar por longitudEpicentro
    }
}


