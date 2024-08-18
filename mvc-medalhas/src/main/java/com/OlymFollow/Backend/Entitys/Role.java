package com.OlymFollow.Backend.Entitys;

import com.OlymFollow.Backend.Dtos.RoleDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;

@Entity(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;

    public Role() {

    }
    public Role(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Role(RoleDto roleDto) {
        this.id = roleDto.id();
        this.role = roleDto.role();
    }


    @Override
    public String getAuthority() {
      return role;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}