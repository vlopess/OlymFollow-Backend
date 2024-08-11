package com.OlymFollow.Backend.Dtos;

import com.OlymFollow.Backend.Entitys.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UserRegisterDTO {
    private Long id;
    @NotBlank(message="Enter username")
    private String username;
    @Email
    private String email;
    @NotBlank(message="Enter password")
    private String password;
    @NotNull
    private List<RoleDto> roles;


    public UserRegisterDTO(){}

    public UserRegisterDTO(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserRegisterDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.roles = user.getRoles().stream().map(RoleDto::new).toList();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public List<RoleDto> getRoles() {
        return roles;
    }
}
