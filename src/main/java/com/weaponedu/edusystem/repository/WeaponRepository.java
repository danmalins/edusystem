package com.weaponedu.edusystem.repository;

import com.weaponedu.edusystem.model.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Weapon - тип сутності, Long - тип ID
@Repository // Хоча це не обов'язково для JpaRepository, це хороша практика.
public interface WeaponRepository extends JpaRepository<Weapon, Long> {
    List<Weapon> findBySecretLevelLessThanEqual(int secretLevel);

    //Optional<Weapon> findByName(String name);
}