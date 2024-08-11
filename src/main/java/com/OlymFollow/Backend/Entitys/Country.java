package com.OlymFollow.Backend.Entitys;

import com.OlymFollow.Backend.Models.Medal;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "pais")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nome;
    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Medalha> medalhas = new ArrayList<>();

    public Country() {}

    public Country(Long id, String nome) {
        this.id = id;
        this.nome = nome.toLowerCase();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    public List<Medalha> getMedalhas() {
        return medalhas;
    }

    public int getNumberOfGolds() {
        return medalhas.stream().filter(medalha -> medalha.getMedalha().equals(Medal.OURO)).toList().size();
    }
    public int getNumberOfSilvers() {
        return medalhas.stream().filter(medalha -> medalha.getMedalha().equals(Medal.PRATA)).toList().size();
    }
    public int getNumberOfBronze() {
        return medalhas.stream().filter(medalha -> medalha.getMedalha().equals(Medal.BRONZE)).toList().size();
    }
    public int getNumberOfMedal() {
        return medalhas.size();
    }
}
