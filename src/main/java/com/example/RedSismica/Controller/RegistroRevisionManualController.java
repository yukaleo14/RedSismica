package com.example.RedSismica.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RedSismica.Controller.DTO.EventoDTO;
import com.example.RedSismica.Controller.DTO.RegistroRevisionManualDTO;
import com.example.RedSismica.Service.RevisionManualService;

@RestController
@RequestMapping("/api/revisiones")
public class RegistroRevisionManualController {

    private final RevisionManualService revisionManualService;

    @Autowired
    public RegistroRevisionManualController(RevisionManualService revisionManualService) {
        this.revisionManualService = revisionManualService;
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<EventoDTO>> obtenerEventosPendientes() {
        List<EventoDTO> eventosPendientes = revisionManualService.obtenerEventosPendientesRevision();
        if (eventosPendientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(eventosPendientes, HttpStatus.OK);
    }

    @GetMapping("/eventos/{id}")
    public ResponseEntity<EventoDTO> obtenerDetalleEvento(@PathVariable Long id) {
        try {
            EventoDTO eventoDetalle = revisionManualService.obtenerDetallesEvento(id);
            return new ResponseEntity<>(eventoDetalle, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> registrarRevision(@RequestBody RegistroRevisionManualDTO registroDTO) {
        try {
            revisionManualService.registrarResultadoRevisionManual(registroDTO);
            return new ResponseEntity<>("Revisión registrada exitosamente.", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al registrar la revisión.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
