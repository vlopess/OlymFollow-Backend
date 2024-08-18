package com.OlymFollow.Backend.Controllers;

import com.OlymFollow.Backend.Entitys.Esporte;
import com.OlymFollow.Backend.Services.EsporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/esportes")
public class EsporteController {
    private final EsporteService esporteService;
    
    @Autowired
    public EsporteController(EsporteService esporteService) {
        this.esporteService = esporteService;
    }
    
    @GetMapping
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Pega todos os esportes cadastrados", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<Esporte>> getEsportes() {
        var esportes = esporteService.buscarTodos();
        return ResponseEntity.ok(esportes);
    }
}
