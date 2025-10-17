package com.weaponedu.edusystem.repository;

import com.weaponedu.edusystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// User - тип сутності, Long - тип ID
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Знаходить користувача за його логіном.
     * Необхідно для Spring Security (UserDetailsServiceImpl).
     */
    User findByUsername(String username);

    /**
     * Перевіряє, чи існує користувач з таким логіном.
     * Необхідно для запобігання дублюванню під час реєстрації.
     */
    Boolean existsByUsername(String username);
}