package com.example.RedSismica.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.RedSismica.jwt.jwtAutenticacionFiltro;

import lombok.RequiredArgsConstructor;



@Configuration //esta clase tiene metodos que estan marcado con @bean
@EnableWebMvc
@RequiredArgsConstructor
//clase que tiene todos los filtros de seguridad
public class SecurityConfig {

    private final jwtAutenticacionFiltro jwtAutenticacionFiltro; //filtro de autenticacion JWT
    private final AuthenticationProvider authProvider; //proveedor de autenticacion

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       return http
            .csrf(csrf -> csrf.disable()) //deshabilita la proteccion CSRF, no recomendado para produccion
            .authorizeHttpRequests(authRequest -> authRequest
                .requestMatchers("/autenticacion/**").permitAll() //permitir todas las peticiones a autenticacion
                .anyRequest().authenticated() //cualquier peticion que no sea autenticacion debe estar autenticado 
            )
            .sessionManagement(sessionManager -> 
            sessionManager
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //configura la politica de sesion como sin estado
            .authenticationProvider(authProvider) //configura el proveedor de autenticacion
            .addFilterBefore(jwtAutenticacionFiltro, UsernamePasswordAuthenticationFilter.class) //agrega el filtro JWT antes del filtro de autenticacion por nombre de usuario y contraseña
            .build();
    }

}
