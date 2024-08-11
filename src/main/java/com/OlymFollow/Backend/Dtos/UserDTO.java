package com.OlymFollow.Backend.Dtos;

import com.OlymFollow.Backend.Entitys.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class UserDTO {
    private Long id;
    @NotBlank(message="Enter username")
    private String username;
    @Email
    private String email;
    @NotBlank(message="Enter password")
    private String password;
    @NotNull
    private List<RoleDto> roles;

    public UserDTO(){}

    public UserDTO(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }
}
