package com.weaponedu.edusystem.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AuthResponseDTO {
    private String accessToken;
}
