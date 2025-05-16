package com.example.RedSismica.Boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import com.example.RedSismica.Controller.GestorAdmResultadoEventoSismico;
import com.example.RedSismica.Model.EventoSismico;;

public class PantallaAdmResultadoEventoSismico extends JFrame {
    // Componentes de la interfaz
    private JButton btnRegistrarResultado;
    private JList<EventoSismico> listaEventos;
    private DefaultListModel<EventoSismico> modeloListaEventos;
    private JButton btnSeleccionarEvento;
    private JButton btnRechazarEvento;
    private JButton btnAprobarEvento;
    private JLabel lblEstado;
    private JPanel panelPrincipal;
    
    // Referencia al gestor
    private GestorAdmResultadoEventoSismico gestor;
    
    // Constructor
    public PantallaAdmResultadoEventoSismico(GestorAdmResultadoEventoSismico gestor) {
        this.gestor = gestor;
        inicializarComponentes();
        configurarVentana();
    }
    
    private void inicializarComponentes() {
        // Configuración de componentes
        panelPrincipal = new JPanel(new BorderLayout(10, 10));
        
        // Botón para registrar resultado
        btnRegistrarResultado = new JButton("Registrar Resultado de Revisión");
        btnRegistrarResultado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OpcionRegistrarResultRevEventoSismico();
            }
        });
        
        // Lista de eventos sísmicos
        modeloListaEventos = new DefaultListModel<>();
        listaEventos = new JList<>(modeloListaEventos);
        listaEventos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listaEventos);
        
        // Panel de botones de acción
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnSeleccionarEvento = new JButton("Seleccionar Evento");
        btnRechazarEvento = new JButton("Rechazar Evento");
        btnAprobarEvento = new JButton("Aprobar Evento");
        lblEstado = new JLabel("Estado: ");
        
        btnSeleccionarEvento.addActionListener(e -> TomarSeleccionEventoSismico());
        btnRechazarEvento.addActionListener(e -> TomarSeleccionRechazarEvento());
        
        panelBotones.add(btnSeleccionarEvento);
        panelBotones.add(btnRechazarEvento);
        panelBotones.add(btnAprobarEvento);
        panelBotones.add(lblEstado);
        
        // Agregar componentes al panel principal
        panelPrincipal.add(btnRegistrarResultado, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        // Configurar estado inicial de los componentes
        btnSeleccionarEvento.setEnabled(false);
        btnRechazarEvento.setEnabled(false);
        btnAprobarEvento.setEnabled(false);
    }
    
    private void configurarVentana() {
        this.setTitle("Administración de Resultados de Eventos Sísmicos");
        this.setContentPane(panelPrincipal);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
    }
    
    // Métodos públicos
    
    public void OpcionRegistrarResultRevEventoSismico() {
        // Invoca al gestor para registrar resultados
        gestor.OpcionRegistrarResultRevEventoSismico();
        HabilitarVentana(true);
    }
    
    public void HabilitarVentana(boolean habilitar) {
        // Habilita o deshabilita componentes de la ventana
        btnRegistrarResultado.setEnabled(habilitar);
        listaEventos.setEnabled(habilitar);
        btnSeleccionarEvento.setEnabled(habilitar);
        
        if (habilitar) {
            // Actualizar la lista de eventos cuando se habilita
            List<EventoSismico> eventos = gestor.BuscarEventosSismicosAuto();
            MostrarEventosSismicosParaSeleccion(eventos);
        }
    }
    
    public void MostrarEventosSismicosParaSeleccion(List<EventoSismico> eventos) {
        // Muestra los eventos sísmicos en la lista para selección
        modeloListaEventos.clear();
        for (EventoSismico evento : eventos) {
            modeloListaEventos.addElement(evento);
        }
    }
    
    public void TomarSeleccionEventoSismico() {
        // Toma la selección del usuario de la lista
        EventoSismico eventoSeleccionado = listaEventos.getSelectedValue();
        if (eventoSeleccionado != null) {
            gestor.TomarSeleccionEventoSismico(eventoSeleccionado);
            SolicitarSeleccionAccion();
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un evento", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void SolicitarSeleccionAccion() {
        // Habilita las opciones de acción para el evento seleccionado
        boolean eventoBloqueado = gestor.BuscarEstadoBloqueado();
        
        btnRechazarEvento.setEnabled(!eventoBloqueado);
        btnAprobarEvento.setEnabled(!eventoBloqueado);
        
        lblEstado.setText("Estado: " + (eventoBloqueado ? "BLOQUEADO" : "DISPONIBLE"));
    }
    
    public void TomarSeleccionRechazarEvento() {
        // Procesa la selección de rechazar evento
        int confirmacion = JOptionPane.showConfirmDialog(
            this, 
            "¿Está seguro que desea rechazar este evento sísmico?", 
            "Confirmar rechazo", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            gestor.TomarSeleccionRechazarEvento();
            JOptionPane.showMessageDialog(this, "Evento rechazado correctamente");
            HabilitarVentana(false);
        }
    }
    
    // Método para mostrar/ocultar la ventana
    public void mostrar(boolean visible) {
        this.setVisible(visible);
        if (visible) {
            HabilitarVentana(true);
        }
    }
}
