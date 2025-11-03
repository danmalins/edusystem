package com.weaponedu.edusystem.controller;

import com.weaponedu.edusystem.dto.ArticleUpdateRequestDTO;
import com.weaponedu.edusystem.model.Article;
import com.weaponedu.edusystem.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article, Authentication authentication) {
        Article createdArticle = articleService.createArticle(article, authentication);
        return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles(Authentication authentication) {
        List<Article> articles = articleService.getAllArticles(authentication);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id, Authentication authentication) {
        Article article = articleService.getArticleById(id, authentication);
        return ResponseEntity.ok(article);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(
            @PathVariable Long id,
            @RequestBody ArticleUpdateRequestDTO updatedArticle,
            Authentication authentication) {

        Article article = articleService.updateArticle(id, updatedArticle, authentication);
        return ResponseEntity.ok(article);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id, Authentication authentication) {
        articleService.deleteArticle(id, authentication);
        return ResponseEntity.noContent().build();
    }
}