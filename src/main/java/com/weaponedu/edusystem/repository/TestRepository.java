package com.weaponedu.edusystem.repository;

import com.weaponedu.edusystem.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {}