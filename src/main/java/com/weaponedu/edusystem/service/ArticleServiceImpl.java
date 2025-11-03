package com.weaponedu.edusystem.service;

import com.weaponedu.edusystem.dto.ArticleUpdateRequestDTO;
import com.weaponedu.edusystem.model.Enums.Role;
import com.weaponedu.edusystem.model.User;
import com.weaponedu.edusystem.model.Article;
import com.weaponedu.edusystem.repository.UserRepository;
import com.weaponedu.edusystem.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    private boolean isAdminOrMentor(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals(Role.ROLE_ADMIN.name()) || role.equals(Role.ROLE_MENTOR.name()));
    }

    @Transactional
    @Override
    public Article createArticle(Article article, Authentication authentication) {
        if (!isAdminOrMentor(authentication)) {
            throw new SecurityException("Access Denied: Only Admin or Mentor can create new articles.");
        }

        return articleRepository.save(article);
    }

    @Override
    public List<Article> getAllArticles(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());

        if (user.getRole().equals(Role.ROLE_ADMIN) || user.getRole().equals(Role.ROLE_MENTOR)) {
            return articleRepository.findAll();
        }

        return articleRepository.findBySecretLevelLessThanEqual(user.getSecurityLevel());
    }

    @Override
    public Article getArticleById(Long id, Authentication authentication) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article not found with ID: " + id));

        User user = userRepository.findByEmail(authentication.getName());

        if (article.getSecretLevel() > user.getSecurityLevel()
                && !(user.getRole() == Role.ROLE_ADMIN || user.getRole() == Role.ROLE_MENTOR)) {
            throw new SecurityException("Access denied: insufficient clearance level.");
        }

        return article;
    }

    @Transactional
    @Override
    public Article updateArticle(Long id, ArticleUpdateRequestDTO updatedArticle, Authentication authentication) {
        if (!isAdminOrMentor(authentication)) {
            throw new SecurityException("Access Denied: Only Admin or Mentor can create new articles.");
        }

        Article existingArticle = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article not found with ID: " + id));

        existingArticle.setName(updatedArticle.getName());
        existingArticle.setType(updatedArticle.getType());
        existingArticle.setDescription(updatedArticle.getDescription());
        existingArticle.setSecretLevel(updatedArticle.getSecretLevel());

        return articleRepository.save(existingArticle);
    }

    @Transactional
    @Override
    public void deleteArticle(Long id, Authentication authentication ) {
        if (!isAdminOrMentor(authentication)) {
            throw new SecurityException("Access Denied: Only Admin or Mentor can delete articles.");
        }
        if (!articleRepository.existsById(id)) {
            throw new EntityNotFoundException("Article not found with ID: " + id);
        }
        articleRepository.deleteById(id);
    }
}