package com.OlymFollow.Backend.Dtos;

import com.OlymFollow.Backend.Entitys.Medalha;
import com.OlymFollow.Backend.Models.Medal;

public record MedalDetailsDTO(Medal medalha, String nomeAtleta, String esporte, String country) {

    public MedalDetailsDTO(Medalha medal){
        this(medal.getMedalha(), medal.getNomeAtleta(), medal.getEsporte().getNome(), medal.getCountry().getNome());
    }
}


