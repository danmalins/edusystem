package com.weaponedu.edusystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "weapons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Weapon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(length = 2000)
    private String description;

    // Рівень доступу або складності (може бути використано для RBAC або фільтрації)
    private int secretLevel; //(1,2,3)

    // URL або шлях до навчального відео/документації
    private String resourceUrl;
}