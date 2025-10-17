package com.weaponedu.edusystem.service;

import com.weaponedu.edusystem.model.Weapon;

import java.util.List;

public interface WeaponService {
    Weapon createWeapon(Weapon weapon);
    List<Weapon> getAllWeapons();
    Weapon getWeaponById(Long id);
    Weapon updateWeapon(Long id, Weapon updatedWeapon);
    void deleteWeapon(Long id);
}