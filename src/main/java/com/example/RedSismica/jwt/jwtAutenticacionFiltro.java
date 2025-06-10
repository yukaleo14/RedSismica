package com.example.RedSismica.jwt;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

@Component
@RequiredArgsConstructor
public class jwtAutenticacionFiltro extends OncePerRequestFilter {

    private final JwtService jwtService; // Asumiendo que tienes un servicio JWT para generar tokens
    private final UserDetailsService userDetailsService; // Repositorio de usuarios

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
        throws ServletException, IOException {
        // Aquí se implementaría la lógica de autenticación JWT
        // Por ejemplo, verificar el token JWT en los encabezados de la solicitud

        // Continuar con la cadena de filtros
        final String token =getTokenFromRequest(request);
        final String username;

        if (token == null) {
            // Si no hay token, se puede enviar una respuesta de error
            filterChain.doFilter(request, response);
            return;
        }
        username=jwtService.getUsernameFromToken(token);
// Si el token es válido, se puede cargar los detalles del usuario
        // y establecer la autenticación en el contexto de seguridad
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails=userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken
                        (userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));                        
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);

    }
    private String getTokenFromRequest(HttpServletRequest request) {
        // Aquí se implementaría la lógica para extraer el token JWT del encabezado de la solicitud
        final String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasText(authHeader)&& authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // Extrae el token después de "Bearer "
        }
        return null; // Si no hay token, devuelve null
    }
}
 