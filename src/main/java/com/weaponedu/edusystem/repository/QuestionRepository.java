package com.weaponedu.edusystem.repository;


import com.weaponedu.edusystem.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {}
