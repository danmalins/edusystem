package com.weaponedu.edusystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TestResultDTO {
    private int totalQuestions;
    private int correctAnswers;
    private double scorePercentage;
}
