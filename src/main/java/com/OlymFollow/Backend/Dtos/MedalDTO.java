package com.OlymFollow.Backend.Dtos;

import com.OlymFollow.Backend.Entitys.Country;
import com.OlymFollow.Backend.Entitys.Esporte;
import com.OlymFollow.Backend.Entitys.Medalha;
import com.OlymFollow.Backend.Models.Medal;
import com.fasterxml.jackson.annotation.JsonIgnore;

public record MedalDTO(Medal medalha, String nomeAtleta, Esporte esporte, Country country) {

    public MedalDTO(Medalha medal){
        this(medal.getMedalha(), medal.getNomeAtleta(), medal.getEsporte(), null);
    }
}
