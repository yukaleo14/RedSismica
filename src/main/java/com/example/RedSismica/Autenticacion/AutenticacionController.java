package com.example.RedSismica.Autenticacion;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/autenticacion")
@RequiredArgsConstructor
public class AutenticacionController {

    private final AutenticacionService autenticacionService;

    @PostMapping(value = "login")
    public ResponseEntity<AutenticacionResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(autenticacionService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AutenticacionResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(autenticacionService.register(request));
    }
}
