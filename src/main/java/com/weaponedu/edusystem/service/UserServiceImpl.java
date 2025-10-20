package com.weaponedu.edusystem.service;

import com.weaponedu.edusystem.dto.AuthRequestDTO;
import com.weaponedu.edusystem.dto.UserRegistrationRequestDTO;
import com.weaponedu.edusystem.dto.UserUpdateRequestDTO;
import com.weaponedu.edusystem.model.Enums.Role;
import com.weaponedu.edusystem.model.User;
import com.weaponedu.edusystem.repository.UserRepository;
import com.weaponedu.edusystem.security.MyUserDetails;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Замінюємо @RequiredArgsConstructor на класичний конструктор
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * Перевіряє, чи має користувач роль ROLE_ADMIN.
     */
    private boolean isAdmin(Authentication authentication) {
        // Перевіряємо, чи є серед прав доступу роль ROLE_ADMIN (як рядок)
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals(Role.ROLE_ADMIN.name()));
    }

    // --- CRUD + БЕЗПЕКА ---

    /**
     * Метод для входу та генерації токена.
     */
    @Override
    public String verifyAndReturnToken(AuthRequestDTO userCredentials) {
        try {
            // Аутентифікація через Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userCredentials.getUsername(), userCredentials.getPassword()));

            // Важливо: встановлення контексту не потрібне для генерації токена, але може бути корисним.
            SecurityContextHolder.getContext().setAuthentication(authentication);
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            User authenticatedUser = userDetails.getUser();

            // Генерація та повернення JWT
            return jwtService.generateToken(authenticatedUser.getUsername(), authenticatedUser.getRole().name());

        } catch (BadCredentialsException e) {
            // Кидаємо виняток, якщо облікові дані невірні
            throw new BadCredentialsException("Wrong username or password.");
        }
    }


    /**
     * Реєстрація нового користувача (за замовчуванням ROLE_STUDENT).
     */
    @Override
    @Transactional
    public User registerNewUser(UserRegistrationRequestDTO registrationRequest) {
        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            throw new EntityExistsException("User with username '" + registrationRequest.getUsername() + "' already exists.");
        }

        User user = new User(
                registrationRequest.getUsername(),
                passwordEncoder.encode(registrationRequest.getPassword())
        );


        user.setRole(Role.ROLE_STUDENT);

        return userRepository.save(user);
    }

    // --- READ (З ЗАХИСТОМ) ---

    @Override
    public List<User> getAllUsers(Authentication authentication) {
        // Доступ лише для адміністраторів
        if (!isAdmin(authentication)) {
            throw new SecurityException("Only admin can see all users.");
        }
        return userRepository.findAll();
    }

    @Override
    public User getUserByUsername(String username, Authentication authentication) {
        if (!isAdmin(authentication) && !username.equals(authentication.getName())) {
            throw new SecurityException("You can only view your own profile.");
        }
        if (!userRepository.existsByUsername(username)) {
            throw new EntityExistsException("User with username '" + username + "' already exists.");
        }
        return userRepository.findByUsername(username);
    }

    // --- UPDATE (З ЗАХИСТОМ) ---

    @Override
    @Transactional
    public User updateUser(String username, UserUpdateRequestDTO updateRequestDTO, Authentication authentication) {

        if (userRepository.existsByUsername(username)) {
            throw new EntityExistsException("User with username '" + username + "' already exists.");
        }
        User userToUpdate = getUserByUsername(username, authentication);
        // Перевірка прав: тільки адмін або власник профілю може оновлювати
        if (!isAdmin(authentication) && !userToUpdate.getUsername().equals(authentication.getName())) {
            throw new SecurityException("You can only update your own profile.");
        }

        if (updateRequestDTO.getUsername() != null && !updateRequestDTO.getUsername().isBlank()) {
            userToUpdate.setUsername(updateRequestDTO.getUsername());
        }

        // Оновлення пароля
        if (updateRequestDTO.getPassword() != null && !updateRequestDTO.getPassword().isBlank()) {
            userToUpdate.setPassword(passwordEncoder.encode(updateRequestDTO.getPassword()));
        }

        // TODO: Додайте логіку оновлення ролей тут, якщо це робить ADMIN

        return userRepository.save(userToUpdate);
    }

    // --- DELETE (З ЗАХИСТОМ) ---

    @Override
    @Transactional
    public void deleteUserByUsername(String username, Authentication authentication) {
        // Доступ лише для адміністраторів
        if (!isAdmin(authentication)) {
            throw new SecurityException("Only admin can delete users.");
        }
        if (!userRepository.existsByUsername(username)) {
            throw new EntityExistsException("User with username '" + username + "' already exists.");
        }
        User userToDelete = getUserByUsername(username, authentication);
        userRepository.delete(userToDelete);
    }
}