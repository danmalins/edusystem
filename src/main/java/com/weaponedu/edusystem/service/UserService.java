package com.weaponedu.edusystem.service;

import com.weaponedu.edusystem.dto.AuthRequestDTO;
import com.weaponedu.edusystem.dto.UserRegistrationRequestDTO;
import com.weaponedu.edusystem.dto.UserUpdateRequestDTO; // Потрібно створити цей DTO
import com.weaponedu.edusystem.dto.UserUpdateRoleRequestDTO;
import com.weaponedu.edusystem.model.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    String verifyAndReturnToken(AuthRequestDTO userCredentials); // Використовуємо LoginRequest як вхід
    User registerNewUser(UserRegistrationRequestDTO registrationRequest);
    List<User> getAllUsers(Authentication authentication);
    User getUserByUsername(String username, Authentication authentication);
    User updateUser(String username, UserUpdateRequestDTO updateRequestDTO, Authentication authentication);
    void deleteUserByUsername(String username, Authentication authentication);
    User updateUserRole(String username, UserUpdateRoleRequestDTO userUpdateRoleRequestDTO, Authentication authentication);
    void updateUserSecurityLevel(String username, int level, Authentication authentication);
}