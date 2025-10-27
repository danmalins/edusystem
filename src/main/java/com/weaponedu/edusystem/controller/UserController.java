package com.weaponedu.edusystem.controller;

import com.weaponedu.edusystem.dto.*;
import com.weaponedu.edusystem.model.Enums.Role;
import com.weaponedu.edusystem.model.User;
import com.weaponedu.edusystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequestDTO signUpRequest) {
        userService.registerNewUser(signUpRequest);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequestDTO) {
        String token = userService.verifyAndReturnToken(authRequestDTO);
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(Authentication authentication) {
        List<User> users = userService.getAllUsers(authentication);
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName(), authentication);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username, Authentication authentication) {
        User user = userService.getUserByUsername(username, authentication);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username,
            @RequestBody UserUpdateRequestDTO updateDTO,
            Authentication authentication) {
        User updatedUser = userService.updateUser(username, updateDTO, authentication);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("{username}/security-level")
    public ResponseEntity<String> updateUserSecurityLevel(
            @PathVariable String username,
            @RequestBody SecurityLevelRequestDTO securityLevelRequestDTO,
            Authentication authentication) {

        // Оновлюємо рівень користувача, якого передали в URL
        userService.updateUserSecurityLevel(username, securityLevelRequestDTO.getSecurityLevel(), authentication);
        return ResponseEntity.ok("Security level updated successfully for user " + username);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username, Authentication authentication) {
        userService.deleteUserByUsername(username, authentication);
        return ResponseEntity.ok("User deleted successfully!");
    }

    @PutMapping("/role/{username}")
    public ResponseEntity<User> updateUserRole(@PathVariable String username,
                                               @RequestBody UserUpdateRoleRequestDTO userUpdateRoleRequestDTO, Authentication authentication) {
        User updatedUser = userService.updateUserRole(username, userUpdateRoleRequestDTO, authentication);
        return ResponseEntity.ok(updatedUser);
    }
}
