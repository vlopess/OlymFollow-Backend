package com.OlymFollow.Backend.Dtos;

import com.OlymFollow.Backend.Entitys.Country;
import com.OlymFollow.Backend.Entitys.Esporte;
import com.OlymFollow.Backend.Entitys.Medalha;
import com.OlymFollow.Backend.Models.Medal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

public record MedalDTO(@NotNull String tipoMedalha,@NotNull String nomeAtleta,@NotNull String esporte, @NotNull String countryID) {

    public MedalDTO(Medalha medal){
        this(medal.getMedalha().name(), medal.getNomeAtleta(), medal.getEsporte().getNome(), medal.getCountry().getNome());
    }
}
