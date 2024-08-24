package com.OlymFollow.Backend.Entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.text.Normalizer;

@Entity(name = "esporte")
public class Esporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nome;

    public Esporte() {

    }
    public Esporte(Long id, String nome) {
        this.id = id;
        this.nome = nome.toUpperCase();
    }
    public Esporte(String nome) {
        nome = Normalizer.normalize(nome, Normalizer.Form.NFD);
        this.nome = nome.toUpperCase();
    }
    public String getNome() {
        return nome;
    }
    public Long getId() {
        return id;
    }
}
