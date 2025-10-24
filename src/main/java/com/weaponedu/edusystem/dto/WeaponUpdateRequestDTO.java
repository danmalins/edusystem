package com.weaponedu.edusystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class WeaponUpdateRequestDTO {
    private String name;
    private String type;
    private String description;
    private int secretLevel;
}
