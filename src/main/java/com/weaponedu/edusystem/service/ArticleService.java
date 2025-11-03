package com.weaponedu.edusystem.service;

import com.weaponedu.edusystem.dto.ArticleUpdateRequestDTO;
import com.weaponedu.edusystem.model.Article;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ArticleService {
    Article createArticle(Article article, Authentication authentication);
    List<Article> getAllArticles(Authentication authentication);
    Article getArticleById(Long id, Authentication authentication);
    Article updateArticle(Long id, ArticleUpdateRequestDTO updateRequestDTO, Authentication authentication);
    void deleteArticle(Long id, Authentication authentication);
}