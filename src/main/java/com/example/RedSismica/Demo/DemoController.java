package com.example.RedSismica.Demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("") //no entiendo que iria
@RequiredArgsConstructor
public class DemoController {
    // Este controlador es un ejemplo de un endpoint público que no requiere autenticación
    @PostMapping(value = "demo")
    public String welcome() {
        return "Este es un endpoint demo público";
    }
}
