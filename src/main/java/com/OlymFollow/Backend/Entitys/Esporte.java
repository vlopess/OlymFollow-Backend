package com.OlymFollow.Backend.Entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity(name = "esporte")
public class Esporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @Column(nullable = false, unique = true)
    private String nome;

    public Esporte() {

    }
    public Esporte(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }
    public Long getId() {
        return id;
    }
}
