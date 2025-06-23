package com.example.RedSismica.Service;

import com.example.RedSismica.Usuario.Usuario;

public class SesionService {
    
    public String getUsuarioLogueado(Usuario usuario) {
        return usuario.getNombre();
    }
    
}
