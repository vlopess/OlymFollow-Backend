package com.OlymFollow.Backend.Dtos;

import com.OlymFollow.Backend.Entitys.User;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class UserRegisterDTO {
    private Long id;
    @NotBlank(message="Enter username")
    private String username;
    @Email
    private String email;
    @NotBlank(message="Enter password")
    private String password;
    private String pictureUrl;
    private List<RoleDto> roles = List.of(new RoleDto(1L, "USER_ROLE"));


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

    public UserRegisterDTO(GoogleIdToken.Payload payload) {
        this.username = (String) payload.get("name");
        this.email = payload.getEmail();
        this.pictureUrl = (String) payload.get("picture");
        this.roles = List.of(new RoleDto(1L, "USER_ROLE"));

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
    public String getPictureUrl() { return pictureUrl; }
    public List<RoleDto> getRoles() {
        return roles;
    }
}
