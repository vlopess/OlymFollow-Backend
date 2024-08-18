package com.OlymFollow.Backend.Controllers;

import com.OlymFollow.Backend.Dtos.CountryDetailsDTO;
import com.OlymFollow.Backend.Dtos.MedalDTO;
import com.OlymFollow.Backend.Entitys.Medalha;
import com.OlymFollow.Backend.Services.CountryService;
import com.OlymFollow.Backend.Services.MedalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/OlymFollow")
public class MedalController {
    private final MedalService medalService;
    private final CountryService countryService;


    @Autowired
    public MedalController(MedalService medalService, CountryService countryService) {
        this.medalService = medalService;
        this.countryService = countryService;
    }

    @GetMapping(value = "/medalhas")
    @Operation(summary = "Busca todos as medalhas", description = "Retorna todos os países cadastrados com suas respectivas medalhas")
    public ResponseEntity<List<CountryDetailsDTO>> findAll() throws Exception {
        var medals = countryService.getAllCountriesWithMedals();
        return ResponseEntity.ok(medals);
    }

    @GetMapping(value = "/medalhas/{countryName}")
    @Operation(summary = "Busca todas as medalhas de um determidado país")
    public ResponseEntity<CountryDetailsDTO> findByCountryName(@PathVariable String countryName) throws Exception {
        var medals = countryService.getCountryByName(countryName.toUpperCase());
        return ResponseEntity.ok(medals);
    }


    @GetMapping(value = "/medalhas/latest")
    @Operation(summary = "Busca as últimas medalhas", description = "Retorna as últimas medalhas cadastradas")
    public ResponseEntity<List<MedalDTO>> findLastestMedals() throws Exception {
        var medals = medalService.getLastestMedals();
        return ResponseEntity.ok(medals);
    }

    @PostMapping(value = "/medalhas/cadastrar")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Cadastra uma medalha (ROLE ADMIN)", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<String> save(@RequestBody MedalDTO medalDTO, UriComponentsBuilder uriBuilder) throws Exception {
        Medalha medal = medalService.addMedal(medalDTO);
        URI uri = uriBuilder.path("/medalhas/{id}").buildAndExpand(medal.getId()).toUri();
        return ResponseEntity.created(uri).body(("Medal added!"));
    }
}
