package com.weaponedu.edusystem.controller;

import com.weaponedu.edusystem.dto.AuthRequestDTO;
import com.weaponedu.edusystem.dto.AuthResponseDTO;
import com.weaponedu.edusystem.dto.UserRegistrationRequestDTO;
import com.weaponedu.edusystem.dto.UserUpdateRequestDTO;
import com.weaponedu.edusystem.model.User;
import com.weaponedu.edusystem.service.JwtService;
import com.weaponedu.edusystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
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
}
