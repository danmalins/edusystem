package com.weaponedu.edusystem.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AnswerOptionRequestDTO {
    private Long id;
    private String text;
    private boolean correct;
}