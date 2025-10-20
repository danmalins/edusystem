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
    private Long id; // Унікальний ідентифікатор

    // Назва озброєння (наприклад, "АК-47", "FGM-148 Javelin")
    @Column(nullable = false, unique = true)
    private String name;

    // Тип озброєння (наприклад, "Штурмова гвинтівка", "ПТРК")
    @Column(nullable = false)
    private String type;

    // Опис або призначення
    @Column(length = 2000) // Довгий текст для опису
    private String description;

    // Рівень доступу або складності (може бути використано для RBAC або фільтрації)
    private String difficultyLevel; // Наприклад: "Basic", "Intermediate", "Advanced"

    // URL або шлях до навчального відео/документації
    private String resourceUrl;
}