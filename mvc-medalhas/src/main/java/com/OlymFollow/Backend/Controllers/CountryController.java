package com.OlymFollow.Backend.Controllers;

import com.OlymFollow.Backend.Dtos.CountryDTO;
import com.OlymFollow.Backend.Entitys.Country;
import com.OlymFollow.Backend.Services.CountryService;
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
@RequestMapping(value = "/countries")
public class CountryController {
    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Pega todos os países cadastrados", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<CountryDTO>> getAllCountries() {
        var countries = countryService.getAllCountries();
        return ResponseEntity.ok(countries);
    }
}
