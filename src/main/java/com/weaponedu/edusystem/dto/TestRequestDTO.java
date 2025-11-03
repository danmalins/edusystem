package com.weaponedu.edusystem.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TestRequestDTO {
    private Long id;
    private String title;
    private List<QuestionRequestDTO> questions;
}
