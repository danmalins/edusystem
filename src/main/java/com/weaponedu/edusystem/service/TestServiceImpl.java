package com.weaponedu.edusystem.service;

import com.weaponedu.edusystem.dto.SubmitAnswerDTO;
import com.weaponedu.edusystem.dto.TestResultDTO;
import com.weaponedu.edusystem.model.AnswerOption;
import com.weaponedu.edusystem.model.Enums.Role;
import com.weaponedu.edusystem.model.Question;
import com.weaponedu.edusystem.model.Test;
import com.weaponedu.edusystem.repository.AnswerOptionRepository;
import com.weaponedu.edusystem.repository.QuestionRepository;
import com.weaponedu.edusystem.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final AnswerOptionRepository answerOptionRepository;

    private boolean isAdminOrMentor(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals(Role.ROLE_ADMIN.name()) || role.equals(Role.ROLE_MENTOR.name()));
    }

    @Override
    public List<Test> getAllTests(Authentication authentication) {
        return testRepository.findAll();
    }

    @Override
    public Test getTestById(Long id, Authentication authentication) {
        return testRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Test not found"));
    }

    @Override
    public Test createTest(Test test, Authentication authentication) {
        if (!isAdminOrMentor(authentication)) {
            throw new SecurityException("Access Denied: Only Admin or Mentor can create new weapons.");
        }

        if (test.getQuestions() != null) {
            for (Question question : test.getQuestions()) {
                question.setTest(test);
                if (question.getOptions() != null) {
                    for (AnswerOption option : question.getOptions()) {
                        option.setQuestion(question);
                    }
                }
            }
        }

        return testRepository.save(test);
    }

    @Override
    public TestResultDTO submitTest(Long testId, List<SubmitAnswerDTO> answers, Authentication authentication) {
        Test test = getTestById(testId, authentication);
        int total = test.getQuestions().size();
        int correct = 0;

        // Перетворюємо список у Map для швидкого доступу
        Map<Long, Long> answersMap = answers.stream()
                .collect(Collectors.toMap(SubmitAnswerDTO::getQuestionId, SubmitAnswerDTO::getSelectedOptionId));

        for (Question q : test.getQuestions()) {
            Long selectedOptionId = answersMap.get(q.getId());
            if (selectedOptionId == null) continue;

            AnswerOption selected = answerOptionRepository.findById(selectedOptionId).orElse(null);
            if (selected != null && selected.isCorrect()) correct++;
        }

        double percent = (double) correct / total * 100.0;
        return new TestResultDTO(total, correct, percent);
    }

    @Override
    public void deleteTest(Long testId, Authentication authentication) {
        if (!isAdminOrMentor(authentication)) {
            throw new SecurityException("Access Denied: Only Admin or Mentor can create new weapons.");
        }
        if(!testRepository.existsById(testId)) {
            throw new SecurityException("Test not found");
        }
        testRepository.deleteById(testId);
    }
}