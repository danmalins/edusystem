package com.weaponedu.edusystem.repository;

import com.weaponedu.edusystem.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findBySecretLevelLessThanEqual(int secretLevel);
}