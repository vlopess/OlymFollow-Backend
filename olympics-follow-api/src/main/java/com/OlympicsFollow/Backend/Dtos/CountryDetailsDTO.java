package com.OlymFollow.Backend.Dtos;

import com.OlymFollow.Backend.Entitys.Country;
import com.OlymFollow.Backend.Models.Medal;

import java.util.ArrayList;
import java.util.List;

public class CountryDetailsDTO {
    private Long id;
    private String nome;
    private String urlImage;
    private List<MedalDetailsDTO> medalhas = new ArrayList<>();

    public CountryDetailsDTO() {}

    public CountryDetailsDTO(Long id, String nome, String ulrImage) {
        this.id = id;
        this.nome = nome;
        this.urlImage = ulrImage;
    }

    public CountryDetailsDTO(Country country) {
        this.id = country.getId();
        this.nome = country.getNome();
        this.urlImage = country.getUrlImage();
        this.medalhas = country.getMedalhas().stream().map(MedalDetailsDTO::new).toList();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<MedalDetailsDTO> getMedalhas() {
        return medalhas;
    }
    public String getUrlImage() {
        return urlImage;
    }

    public int getNumberOfGolds() {
        return medalhas.stream().filter(medalha -> medalha.medalha().equals(Medal.OURO)).toList().size();
    }
    public int getNumberOfSilvers() {
        return medalhas.stream().filter(medalha -> medalha.medalha().equals(Medal.PRATA)).toList().size();
    }
    public int getNumberOfBronze() {
        return medalhas.stream().filter(medalha -> medalha.medalha().equals(Medal.BRONZE)).toList().size();
    }
    public int getNumberOfMedal() {
        return medalhas.size();
    }
}
