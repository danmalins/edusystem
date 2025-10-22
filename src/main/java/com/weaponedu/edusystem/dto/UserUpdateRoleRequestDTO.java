package com.weaponedu.edusystem.dto;

import com.weaponedu.edusystem.model.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserUpdateRoleRequestDTO {
    private Role role;
}
