package com.weaponedu.edusystem.service;

import com.weaponedu.edusystem.dto.WeaponUpdateRequestDTO;
import com.weaponedu.edusystem.model.Weapon;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface WeaponService {
    Weapon createWeapon(Weapon weapon, Authentication authentication);
    List<Weapon> getAllWeapons();
    Weapon getWeaponById(Long id);
    Weapon updateWeapon(Long id, WeaponUpdateRequestDTO updateRequestDTO, Authentication authentication);
    void deleteWeapon(Long id, Authentication authentication);
}