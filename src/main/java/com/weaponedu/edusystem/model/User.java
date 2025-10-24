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

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private int securityLevel;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}