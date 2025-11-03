package com.weaponedu.edusystem.service;

import com.weaponedu.edusystem.dto.SubmitAnswerDTO;
import com.weaponedu.edusystem.dto.TestRequestDTO;
import com.weaponedu.edusystem.dto.TestResponseDTO;
import com.weaponedu.edusystem.dto.TestResultDTO;
import com.weaponedu.edusystem.model.Test;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TestService {
    List<TestResponseDTO> getAllTests(Authentication authentication);
    TestRequestDTO getTestById(Long id, Authentication authentication);
    Test createTest(Test test, Authentication authentication);
    TestResultDTO submitTest(Long testId, List<SubmitAnswerDTO> answers, Authentication authentication);
    void deleteTest(Long testId, Authentication authentication);
}
