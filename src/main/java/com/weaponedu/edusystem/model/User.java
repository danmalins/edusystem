package com.weaponedu.edusystem.model;

import com.weaponedu.edusystem.model.Enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username")
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Логін користувача (має бути унікальним)
    private String username;

    // Пароль буде збережений у вигляді хешу
    private String password;

    // Інші поля користувача (email, ім'я, тощо) можна додати тут
    // private String email;

    // Зв'язок Many-to-Many для реалізації RBAC
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Role role;

    // Конструктор для зручності реєстрації
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}