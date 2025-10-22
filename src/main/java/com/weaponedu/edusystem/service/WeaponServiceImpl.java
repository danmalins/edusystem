package com.weaponedu.edusystem.service;

import com.weaponedu.edusystem.dto.WeaponUpdateRequestDTO;
import com.weaponedu.edusystem.model.Enums.Role;
import com.weaponedu.edusystem.model.Weapon;
import com.weaponedu.edusystem.repository.WeaponRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service// Позначає клас як компонент бізнес-логіки
@RequiredArgsConstructor
public class WeaponServiceImpl implements WeaponService{

    private final WeaponRepository weaponRepository;

    private boolean isAdminOrMentor(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals(Role.ROLE_ADMIN.name()) || role.equals(Role.ROLE_MENTOR.name()));
    }

    /**
     * Створити нове озброєння.
     */
    @Transactional
    @Override
    public Weapon createWeapon(Weapon weapon, Authentication authentication) {
        if (!isAdminOrMentor(authentication)) {
            throw new SecurityException("Access Denied: Only Admin or Mentor can create new weapons.");
        }
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
    public Weapon updateWeapon(Long id, WeaponUpdateRequestDTO updatedWeapon, Authentication authentication) {
        if (!isAdminOrMentor(authentication)) {
            throw new SecurityException("Access Denied: Only Admin or Mentor can create new weapons.");
        }

        Weapon existingWeapon = weaponRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Weapon not found with ID: " + id));

        existingWeapon.setName(updatedWeapon.getName());
        existingWeapon.setType(updatedWeapon.getType());
        existingWeapon.setDescription(updatedWeapon.getDescription());

        return weaponRepository.save(existingWeapon);
    }

    /**
     * Видалити озброєння за ID.
     */
    @Transactional
    @Override
    public void deleteWeapon(Long id, Authentication authentication ) {
        if (!isAdminOrMentor(authentication)) {
            throw new SecurityException("Access Denied: Only Admin or Mentor can delete weapons.");
        }
        if (!weaponRepository.existsById(id)) {
            throw new EntityNotFoundException("Weapon not found with ID: " + id);
        }
        weaponRepository.deleteById(id);
    }
}