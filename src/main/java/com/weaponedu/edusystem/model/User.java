package com.weaponedu.edusystem.model;

import com.weaponedu.edusystem.model.Enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "app_user")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Логін користувача (має бути унікальним)
    @Column(nullable = false)
    private String username;

    // Пароль буде збережений у вигляді хешу
    @Column(nullable = false)
    private String password;

    // Інші поля користувача (email, ім'я, тощо) можна додати тут
    // private String email;

    // Зв'язок Many-to-Many для реалізації RBAC
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    // Конструктор для зручності реєстрації
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}