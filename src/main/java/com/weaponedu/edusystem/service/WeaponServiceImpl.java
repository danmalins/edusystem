package com.weaponedu.edusystem.service;

import com.weaponedu.edusystem.dto.WeaponUpdateRequestDTO;
import com.weaponedu.edusystem.model.Enums.Role;
import com.weaponedu.edusystem.model.User;
import com.weaponedu.edusystem.model.Weapon;
import com.weaponedu.edusystem.repository.UserRepository;
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
    private final UserRepository userRepository;

    private boolean isAdminOrMentor(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals(Role.ROLE_ADMIN.name()) || role.equals(Role.ROLE_MENTOR.name()));
    }

    @Transactional
    @Override
    public Weapon createWeapon(Weapon weapon, Authentication authentication) {
        if (!isAdminOrMentor(authentication)) {
            throw new SecurityException("Access Denied: Only Admin or Mentor can create new weapons.");
        }

        return weaponRepository.save(weapon);
    }

    @Override
    public List<Weapon> getAllWeapons(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());

        if (user.getRole().equals(Role.ROLE_ADMIN) || user.getRole().equals(Role.ROLE_MENTOR)) {
            // Ментори та адміні можуть бачити всі матеріали
            return weaponRepository.findAll();
        }

        // Студент бачить тільки матеріали до свого рівня секретності включно
        return weaponRepository.findBySecretLevelLessThanEqual(user.getSecurityLevel());
    }

    @Override
    public Weapon getWeaponById(Long id, Authentication authentication) {
        Weapon weapon = weaponRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Weapon not found with ID: " + id));

        User user = userRepository.findByEmail(authentication.getName());

        if (weapon.getSecretLevel() > user.getSecurityLevel()
                && !(user.getRole() == Role.ROLE_ADMIN || user.getRole() == Role.ROLE_MENTOR)) {
            throw new SecurityException("Access denied: insufficient clearance level.");
        }

        return weapon;
    }

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
        existingWeapon.setSecretLevel(updatedWeapon.getSecretLevel());

        return weaponRepository.save(existingWeapon);
    }

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