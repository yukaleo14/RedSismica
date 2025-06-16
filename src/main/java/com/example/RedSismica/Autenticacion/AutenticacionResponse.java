package com.example.RedSismica.Autenticacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AutenticacionResponse {
    String token;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
