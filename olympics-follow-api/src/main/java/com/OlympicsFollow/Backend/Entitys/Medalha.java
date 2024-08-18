package com.OlymFollow.Backend.Entitys;

import com.OlymFollow.Backend.Dtos.MedalDTO;
import com.OlymFollow.Backend.Dtos.MedalRegisterDTO;
import com.OlymFollow.Backend.Models.Medal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity(name = "medalha")
public class Medalha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String nomeAtleta;
    @Enumerated(EnumType.STRING)
    private Medal medalha;
    @ManyToOne
    private Esporte esporte;
    @ManyToOne
    private Country country;

    public Medalha() {

    }
    public Medalha(MedalDTO model){
        this.medalha = model.medalha();
        this.nomeAtleta = model.nomeAtleta();
        this.esporte = model.esporte();
        this.country = model.country();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNomeAtleta() {
        return nomeAtleta;
    }

    public Medal getMedalha() {
        return medalha;
    }
    public Esporte getEsporte() {
        return esporte;
    }
    public Country getCountry() {
        return country;
    }
}
