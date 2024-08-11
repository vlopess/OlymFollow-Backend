package com.OlymFollow.Backend.Dtos;

import com.OlymFollow.Backend.Entitys.Country;
import com.OlymFollow.Backend.Entitys.Esporte;
import com.OlymFollow.Backend.Entitys.Medalha;
import com.OlymFollow.Backend.Models.Medal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

public record MedalDTO(@NotNull Medal medalha,@NotNull String nomeAtleta,@NotNull Esporte esporte, @NotNull @JsonIgnoreProperties({ "medalhas" })
 Country country) {

    public MedalDTO(Medalha medal){
        this(medal.getMedalha(), medal.getNomeAtleta(), medal.getEsporte(), medal.getCountry());
    }
}
