package com.OlymFollow.Backend.Entitys;

import com.OlymFollow.Backend.Dtos.UserDTO;
import com.OlymFollow.Backend.Dtos.UserRegisterDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;
    private String pictureUrl;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
    private List<Role> roles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "inscricoes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "country_id"})
    )
    private List<Country> inscricoes = new ArrayList<>();

    public User() {

    }

    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.username = userDTO.getUsername();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.pictureUrl = userDTO.getPictureUrl();
        this.roles = userDTO.getRoles().stream().map(Role::new).toList();
    }

    public User(UserRegisterDTO registerDTO) {
        this.username = registerDTO.getUsername();
        this.email = registerDTO.getEmail();
        this.password = registerDTO.getPassword();
        this.roles = registerDTO.getRoles().stream().map(Role::new).toList();
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPictureUrl(){
        return pictureUrl;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return password;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return username;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public List<Country> getInscricoes() {
        return inscricoes;
    }

    public void subscribe(Country country) {
        this.inscricoes.add(new Country(country.getId(), country.getNome()));
    }

    public void unsubscribe(Country country) {
        this.inscricoes.remove(country);
    }
}
