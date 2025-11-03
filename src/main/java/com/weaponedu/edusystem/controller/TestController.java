package com.weaponedu.edusystem.controller;

import com.weaponedu.edusystem.dto.SubmitAnswerDTO;
import com.weaponedu.edusystem.dto.TestRequestDTO;
import com.weaponedu.edusystem.dto.TestResponseDTO;
import com.weaponedu.edusystem.dto.TestResultDTO;
import com.weaponedu.edusystem.model.Test;
import com.weaponedu.edusystem.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping
    public List<TestResponseDTO> getAllTests(Authentication authentication) {
        return testService.getAllTests(authentication);
    }

    @GetMapping("/{id}")
    public TestRequestDTO getTest(@PathVariable Long id, Authentication authentication) {
        return testService.getTestById(id, authentication);
    }

    @PostMapping
    public ResponseEntity<Test> createTest(@RequestBody Test test, Authentication authentication) {
        return ResponseEntity.ok(testService.createTest(test, authentication));
    }

    @PostMapping("/{testId}/submit")
    public ResponseEntity<TestResultDTO> submitTest(
            @PathVariable Long testId,
            @RequestBody List<SubmitAnswerDTO> answers,
            Authentication authentication
    ) {
        return ResponseEntity.ok(testService.submitTest(testId, answers, authentication));
    }

    @DeleteMapping("{testId}")
    public ResponseEntity<Test> deleteTest(@PathVariable Long testId, Authentication authentication) {
        testService.deleteTest(testId, authentication);
        return ResponseEntity.noContent().build();
    }
}