package com.OlymFollow.Backend.Dtos;

import com.OlymFollow.Backend.Entitys.Role;

public record RoleDto(Long id, String role) {
	
	public RoleDto(Role role) {
		this(role.getId(), role.getRole());
	}

}
