package com.OlymFollow.Backend.Dtos;

import com.OlymFollow.Backend.Entitys.Country;

public record CountryDTO(Long id, String nome) {
    public CountryDTO(Country country) {
        this(country.getId(), country.getNome());
    }
}
