package com.example.RedSismica.Service;

import com.example.RedSismica.Usuario.Usuario;

public class SesionService {
    
    public static String getUsuarioLogueado(Usuario usuario) {
        return Usuario.getEmpleadoLogueado(usuario);
    }
    
}
