package com.OlymFollow.Backend.Entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity(name = "esporte")
public class Esporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nome;
    private String urlImage;

    public Esporte() {

    }
    public Esporte(Long id, String nome, String urlImage) {
        this.id = id;
        this.nome = nome.toUpperCase();
        this.urlImage = urlImage;
    }
    public String getNome() {
        return nome;
    }
    public Long getId() {
        return id;
    }
    public String getUrlImage() {
        return urlImage;
    }
}
