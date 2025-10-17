package com.weaponedu.edusystem.service;

import com.weaponedu.edusystem.model.Weapon;
import com.weaponedu.edusystem.repository.WeaponRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // Позначає клас як компонент бізнес-логіки
public class WeaponServiceImpl implements WeaponService{

    private final WeaponRepository weaponRepository;

    // Інжекція WeaponRepository через конструктор
    public WeaponServiceImpl(WeaponRepository weaponRepository) {
        this.weaponRepository = weaponRepository;
    }

    /**
     * Створити нове озброєння.
     */
    @Transactional
    @Override
    public Weapon createWeapon(Weapon weapon) {
        // У більш складній логіці тут можна додати перевірку,
        // чи вже існує озброєння з такою назвою.
        return weaponRepository.save(weapon);
    }

    /**
     * Отримати список всього озброєння.
     */
    @Override
    public List<Weapon> getAllWeapons() {
        return weaponRepository.findAll();
    }

    /**
     * Отримати озброєння за ID.
     */
    @Override
    public Weapon getWeaponById(Long id) {
        return weaponRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Weapon not found with ID: " + id));
    }

    /**
     * Оновити існуюче озброєння.
     * @param id ID озброєння для оновлення.
     * @param updatedWeapon Нові дані.
     */
    @Transactional
    @Override
    public Weapon updateWeapon(Long id, Weapon updatedWeapon) {
        // 1. Знайти існуючий об'єкт або кинути виняток
        Weapon existingWeapon = weaponRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Weapon not found with ID: " + id));

        // 2. Оновити поля
        existingWeapon.setName(updatedWeapon.getName());
        existingWeapon.setType(updatedWeapon.getType());
        existingWeapon.setDescription(updatedWeapon.getDescription());
        existingWeapon.setDifficultyLevel(updatedWeapon.getDifficultyLevel());
        existingWeapon.setResourceUrl(updatedWeapon.getResourceUrl());

        // 3. Зберегти оновлений об'єкт
        return weaponRepository.save(existingWeapon);
    }

    /**
     * Видалити озброєння за ID.
     */
    @Transactional
    @Override
    public void deleteWeapon(Long id) {
        if (!weaponRepository.existsById(id)) {
            throw new EntityNotFoundException("Weapon not found with ID: " + id);
        }
        weaponRepository.deleteById(id);
    }
}