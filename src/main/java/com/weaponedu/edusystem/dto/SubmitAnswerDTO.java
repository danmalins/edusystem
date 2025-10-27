package com.weaponedu.edusystem.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SubmitAnswerDTO {
    private Long questionId;
    private Long selectedOptionId;
}