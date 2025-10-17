package com.weaponedu.edusystem.repository;

import com.weaponedu.edusystem.model.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Weapon - тип сутності, Long - тип ID
@Repository // Хоча це не обов'язково для JpaRepository, це хороша практика.
public interface WeaponRepository extends JpaRepository<Weapon, Long> {

    // Spring Data JPA автоматично надасть такі методи, як:
    // 1. save() - для створення та оновлення
    // 2. findById() - для отримання за ID
    // 3. findAll() - для отримання всіх
    // 4. delete() - для видалення

    // Тут ми можемо додати додаткові методи, якщо вони знадобляться:
    // Optional<Weapon> findByName(String name);
    // List<Weapon> findAllByDifficultyLevel(String level);
}