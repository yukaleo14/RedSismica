package com.example.RedSismica.Autenticacion;

import org.springframework.stereotype.Service;

import com.example.RedSismica.Usuario.Usuario;
import com.example.RedSismica.jwt.JwtService;
import com.example.RedSismica.Usuario.Rol;
import com.example.RedSismica.Usuario.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutenticacionService {
    private final UserRepository userRepository;
    private final JwtService jwtService; // Asumiendo que tienes un servicio JWT para generar tokens
    public AutenticacionResponse login(LoginRequest request) {
      
        return null;
    }

    public AutenticacionResponse register(RegisterRequest request) {
       Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .nombre(request.getNombre())
                .rol(Rol.USUARIO) // Asumiendo que tienes un enum Role con USUARIO como uno de los valores
                .build();
        userRepository.save(usuario);
        return AutenticacionResponse.builder()
        .token(jwtService.getToken(usuario)) // Aquí deberías generar un token JWT o similar
        .build();
        
    }
}
