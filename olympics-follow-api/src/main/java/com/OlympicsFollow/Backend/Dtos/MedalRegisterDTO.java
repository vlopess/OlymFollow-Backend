package com.OlymFollow.Backend.Dtos;

import com.OlymFollow.Backend.Entitys.Country;
import com.OlymFollow.Backend.Entitys.Esporte;
import com.OlymFollow.Backend.Models.Medal;
import jakarta.validation.constraints.NotNull;

public record MedalRegisterDTO(@NotNull Medal medalha, @NotNull String nomeAtleta, @NotNull int esporteID, @NotNull int countryID) {
}
