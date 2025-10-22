package com.weaponedu.edusystem.service;

import com.weaponedu.edusystem.dto.AuthRequestDTO;
import com.weaponedu.edusystem.dto.UserRegistrationRequestDTO;
import com.weaponedu.edusystem.dto.UserUpdateRequestDTO; // Потрібно створити цей DTO
import com.weaponedu.edusystem.dto.UserUpdateRoleRequestDTO;
import com.weaponedu.edusystem.model.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    // Аутентифікація та JWT
    String verifyAndReturnToken(AuthRequestDTO userCredentials); // Використовуємо LoginRequest як вхід

    // Реєстрація
    User registerNewUser(UserRegistrationRequestDTO registrationRequest);

    // READ
    List<User> getAllUsers(Authentication authentication);
    User getUserByUsername(String username, Authentication authentication);

    // UPDATE
    User updateUser(String username, UserUpdateRequestDTO updateRequestDTO, Authentication authentication);

    // DELETE
    void deleteUserByUsername(String username, Authentication authentication);

    User updateUserRole(String username, UserUpdateRoleRequestDTO userUpdateRoleRequestDTO, Authentication authentication);
}