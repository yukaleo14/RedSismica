package com.example.RedSismica.Iterator;
import com.example.RedSismica.Model.EventoSismico;
import com.example.RedSismica.interfaces.IIterator;

public class IteradorEventoSismico implements IIterator {
    private int actual; // 
    private EventoSismico[] elementos; // [cite: 16]

    // Constructor llamado por el Gestor 
    public IteradorEventoSismico(Object[] elementos) {
        if (elementos == null) {
            this.elementos = new EventoSismico[0];
        } else {
            this.elementos = new EventoSismico[elementos.length];
            for (int i = 0; i < elementos.length; i++) {
                this.elementos[i] = (EventoSismico) elementos[i];
            }
        }
        this.actual = 0;
    }

    @Override
    public Object actual() { // 
        if (haTerminado()) {
            throw new IndexOutOfBoundsException("No hay más elementos");
        }
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
        if (!haTerminado()) {
            actual++;
        }
    }

    @Override
    public boolean cumpleFiltro(Object[] filtros) {
        if (haTerminado()) return false;
        EventoSismico evento = elementos[actual];
        if (evento == null) return false;
       
        
        boolean autoDetectado = evento.esAutoDetectado();
        boolean pendienteRevision = evento.esPendienteRevision();

        if (filtros == null || filtros.length == 0) {
            return autoDetectado || pendienteRevision;
        }

        return (autoDetectado) || ( pendienteRevision);
    }
}
