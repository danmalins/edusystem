package com.weaponedu.edusystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserRegistrationRequestDTO {
    private String username;
    private String password;
}
