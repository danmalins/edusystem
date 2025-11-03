package com.weaponedu.edusystem.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class QuestionResponseDTO {
    private Long id;
    private String text;
    private List<AnswerOptionResponseDTO> options;
}