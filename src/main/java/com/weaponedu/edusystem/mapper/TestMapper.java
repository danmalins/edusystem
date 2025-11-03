package com.weaponedu.edusystem.mapper;



import com.weaponedu.edusystem.dto.*;
import com.weaponedu.edusystem.model.AnswerOption;
import com.weaponedu.edusystem.model.Question;
import com.weaponedu.edusystem.model.Test;

import java.util.stream.Collectors;

public class TestMapper {

    public static TestRequestDTO toTestRequestDTO(Test test) {
        return TestRequestDTO.builder()
                .id(test.getId())
                .title(test.getTitle())
                .questions(test.getQuestions() != null ?
                        test.getQuestions().stream()
                                .map(TestMapper::toQuestionRequestDTO)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    public static TestResponseDTO toTestResponseDTO(Test test) {
        return TestResponseDTO.builder()
                .id(test.getId())
                .title(test.getTitle())
                .build();
    }

    private static QuestionRequestDTO toQuestionRequestDTO(Question question) {
        return QuestionRequestDTO.builder()
                .id(question.getId())
                .text(question.getText())
                .options(question.getOptions() != null ?
                        question.getOptions().stream()
                                .map(TestMapper::toAnswerOptionResponseDTO)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    private static QuestionResponseDTO toQuestionResponseDTO(Question question) {
        return QuestionResponseDTO.builder()
                .id(question.getId())
                .text(question.getText())
                .options(question.getOptions() != null ?
                        question.getOptions().stream()
                                .map(TestMapper::toAnswerOptionResponseDTO)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    private static AnswerOptionRequestDTO toAnswerOptionRequestDTO(AnswerOption option) {
        return AnswerOptionRequestDTO.builder()
                .id(option.getId())
                .text(option.getText())
                .correct(option.isCorrect())
                .build();
    }

    private static AnswerOptionResponseDTO toAnswerOptionResponseDTO(AnswerOption option) {
        return AnswerOptionResponseDTO.builder()
                .id(option.getId())
                .text(option.getText())
                .build();
    }
}