package com.weaponedu.edusystem.controller;

import com.weaponedu.edusystem.dto.WeaponUpdateRequestDTO;
import com.weaponedu.edusystem.model.Weapon;
import com.weaponedu.edusystem.service.UserService;
import com.weaponedu.edusystem.service.WeaponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weapons")
@RequiredArgsConstructor
public class WeaponController {

    private final WeaponService weaponService;
    private final UserService userService;

    // --- CREATE ---

    @PostMapping
    public ResponseEntity<Weapon> createWeapon(@RequestBody Weapon weapon, Authentication authentication) {
        Weapon createdWeapon = weaponService.createWeapon(weapon, authentication);
        return new ResponseEntity<>(createdWeapon, HttpStatus.CREATED);
    }

    // --- READ ---

    @GetMapping
    public ResponseEntity<List<Weapon>> getAllWeapons(Authentication authentication) {
        List<Weapon> weapons = weaponService.getAllWeapons(authentication);
        return ResponseEntity.ok(weapons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Weapon> getWeaponById(@PathVariable Long id, Authentication authentication) {
        Weapon weapon = weaponService.getWeaponById(id, authentication);
        return ResponseEntity.ok(weapon);
    }

    // --- UPDATE ---
    @PutMapping("/{id}")
    public ResponseEntity<Weapon> updateWeapon(
            @PathVariable Long id,
            @RequestBody WeaponUpdateRequestDTO updatedWeapon,
            Authentication authentication) {

        Weapon weapon = weaponService.updateWeapon(id, updatedWeapon, authentication);
        return ResponseEntity.ok(weapon);
    }

    // --- DELETE ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWeapon(@PathVariable Long id, Authentication authentication) {
        weaponService.deleteWeapon(id, authentication);
        return ResponseEntity.noContent().build();
    }
}