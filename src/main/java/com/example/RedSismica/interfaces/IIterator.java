package com.example.RedSismica.interfaces;

public interface IIterator {
    Object actual(); 
    boolean haTerminado(); 
    void primero(); 
    void siguiente(); 
    boolean cumpleFiltro(Object[] filtros); 
    
}