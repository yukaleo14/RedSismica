package com.example.RedSismica.Iterator;
import com.example.RedSismica.Model.EventoSismico;
import com.example.RedSismica.interfaces.IIterator;

public class IteradorEventoSismico implements IIterator {
    private int actual; // 
    private EventoSismico[] elementos; // [cite: 16]

    // Constructor llamado por el Gestor 
    public IteradorEventoSismico(Object[] elementos) {
        this.elementos = new EventoSismico[elementos.length];
        for (int i = 0; i < elementos.length; i++) {
            this.elementos[i] = (EventoSismico) elementos[i];
        }
        this.actual = 0;
    }

    @Override
    public Object esActual() { // 
        return elementos[actual];
    }

    @Override
    public boolean haTerminado() { // 
        return actual >= elementos.length;
    }

    @Override
    public void primero() { // 
        actual = 0;
    }

    @Override
    public void siguiente() { // 
        actual++;
    }

    @Override
    // Este método implementa la lógica de filtrado del diagrama de secuencia [cite: 19, 58]
    public boolean cumpleFiltro(Object[] filtros) {
        // Los "filtros" podrían usarse para más criterios, pero 
        // el diagrama de secuencia solo muestra estas dos validaciones.
        
        if (haTerminado()) {
            return false;
        }

        EventoSismico eventoActual = elementos[actual];
        
        // El iterador llama a los métodos del evento 
        boolean autoDetectado = eventoActual.esAutoDetectado();
        boolean pendienteRevision = eventoActual.esPendienteRevision();
        
        // Lógica de filtro: El diagrama de secuencia 
        // implica que busca eventos que sean AMBOS.
        // Asumiremos que el filtro busca "Autodetectado" Y "Pendiente de Revisión"
        // NOTA: La lógica real puede variar, pero seguimos la secuencia.
        // Es más probable que el filtro sea (esAutodectado Y esPendienteRevision).
        // Si el diagrama [cite: 59] y [cite: 61] son filtros *separados*, 
        // la lógica cambiaría.
        
        // Siguiendo la secuencia, parece que comprueba ambos.
        // Vamos a asumir que el filtro es (esAutodectado() Y esPendienteRevision())
        // o (esAutodectado() O esPendienteRevision())
        // Por el diagrama de secuencia, parece que busca los que cumplen AMBAS:
        
        // Simulación: Filtramos los que son "Autodetectado" Y "Pendiente"
        // ¡Esto es una contradicción lógica en los nombres!
        // Es más probable que el filtro sea para "Eventos Autodetectados que están Pendientes de Revisión".
        // O que el filtro sea `esAutodectado() == true` Y `esPendienteRevision() == true`.
        // Asumiremos que el filtro busca:
        // Eventos "Autodetectados" O eventos "Pendientes de Revisión"
        
        // *** Interpretación más probable del diagrama de secuencia: ***
        // El filtro busca eventos que CUMPLAN las dos condiciones
        // (p.ej., el filtro es [esAutodectado = true, esPendienteRevision = true])
        return autoDetectado && pendienteRevision;
    }
}
