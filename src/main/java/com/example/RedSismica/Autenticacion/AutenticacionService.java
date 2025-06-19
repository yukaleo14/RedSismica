package com.example.RedSismica.Autenticacion;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.RedSismica.Usuario.Usuario;
import com.example.RedSismica.jwt.JwtService;
import com.example.RedSismica.Usuario.Rol;
import com.example.RedSismica.Usuario.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutenticacionService {
    private final UserRepository userRepository;
    private final JwtService jwtService; // Asumiendo que tienes un servicio JWT para generar tokens
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AutenticacionResponse login(LoginRequest request) {
      // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Load user details
        Usuario usuario = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate JWT token
        String token = jwtService.getToken(usuario);

        // Return response
        return AutenticacionResponse.builder()
            .token(token)
            .build();
    }

    public AutenticacionResponse register(RegisterRequest request) {
       Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nombre(request.getNombre())
                .rol(Rol.USUARIO) // Asumiendo que tienes un enum Role con USUARIO como uno de los valores
                .build();
        userRepository.save(usuario);
        return AutenticacionResponse.builder()
        .token(jwtService.getToken(usuario)) // Aquí deberías generar un token JWT o similar
        .build();
        
    }
}
