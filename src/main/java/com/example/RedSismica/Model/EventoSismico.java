package com.example.RedSismica.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn; // Necesario para @JoinColumn
import jakarta.persistence.ManyToOne; // Necesario para @ManyToOne
import jakarta.persistence.OneToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference; // Agrega si no está presente

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

    public static final String esAutoDetectado = null; // Esto parece un error, debería ser una propiedad o constante de clase si aplica

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHoraOcurrencia;
    private LocalDateTime fechaHoraFin;
    private LocalDateTime fechaHoraRevision;
    private Double latitudHipocentro;
    private Double latitudEpicentro;
    private Double longitudEpicentro;
    private Double longitudHipocentro;
    private Double magnitud;
    private String alcance;
    private String origenGeneracion;
    private Boolean autoDetectado;
    private Boolean pendienteRevision;
    private String responsableRevision;

    @ManyToOne
    private EstadoEvento estadoEvento;
    @OneToOne
    private Clasificacion clasificacion;

    // Relación con SerieTemporal (mantener como está)
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SerieTemporal> seriesTemporales = new ArrayList<>();

    // AÑADE ESTA RELACIÓN: Un EventoSismico tiene UNA EstacionSismologica (la principal)
    @OneToMany(mappedBy = "eventoSismico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)// FetchType.LAZY es generalmente mejor para rendimiento
    //@JoinColumn(name = "id_estacion_sismologica") // Esta es la clave foránea en la tabla 'eventos_sismicos'
    private List<EstacionSismologica> estacionesSismologicas = new ArrayList<>();

    public boolean esPendienteRevision() {
        return this.estadoEvento != null && this.estadoEvento.esPendienteRevision();
    }

    public boolean esAutoDetectado() {
        return this.estadoEvento != null && this.estadoEvento.esAutoDetectado();
    }

    public void bloquear() {
        throw new UnsupportedOperationException("Unimplemented method 'bloquear'");
    }

    public Double getDatosPrincipales() {
        return this.latitudEpicentro + this.longitudEpicentro + this.latitudHipocentro + this.longitudHipocentro +this.magnitud; 
    }

    public String getOrigen() {
        throw new UnsupportedOperationException("Unimplemented method 'getOrigen'");
    }

    public List<EstacionSismologica> getEstacionesSismologicas() {
        if (estacionesSismologicas == null) {
            estacionesSismologicas = new ArrayList<>();
        }
        return estacionesSismologicas;
    }
}


