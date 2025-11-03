package com.weaponedu.edusystem.service;

import com.weaponedu.edusystem.dto.AuthRequestDTO;
import com.weaponedu.edusystem.dto.UserRegistrationRequestDTO;
import com.weaponedu.edusystem.dto.UserUpdateRequestDTO;
import com.weaponedu.edusystem.dto.UserUpdateRoleRequestDTO;
import com.weaponedu.edusystem.model.Enums.Role;
import com.weaponedu.edusystem.model.User;
import com.weaponedu.edusystem.repository.UserRepository;
import com.weaponedu.edusystem.security.MyUserDetails;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals(Role.ROLE_ADMIN.name()));
    }

    @Override
    public String verifyAndReturnToken(AuthRequestDTO userCredentials) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userCredentials.getEmail(), userCredentials.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
            User authenticatedUser = myUserDetails.getUser();
            return jwtService.generateToken(authenticatedUser.getEmail(), authenticatedUser.getRole().name());
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Wrong email or password.");
        }
    }

    @Override
    @Transactional
    public User registerNewUser(UserRegistrationRequestDTO registrationRequest) {
        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            throw new EntityExistsException("User with username '" + registrationRequest.getUsername() + "' already exists.");
        }

        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new EntityExistsException("User with email '" + registrationRequest.getEmail() + "' already exists.");
        }

        User user = new User(
                registrationRequest.getUsername(),
                passwordEncoder.encode(registrationRequest.getPassword())
        );


        user.setEmail(registrationRequest.getEmail());
        user.setRole(Role.ROLE_STUDENT);
        user.setSecurityLevel(1);

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers(Authentication authentication) {
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

    @Override
    @Transactional
    public User updateUser(String username, UserUpdateRequestDTO updateRequestDTO, Authentication authentication) {

        if (userRepository.existsByUsername(username)) {
            throw new EntityExistsException("User with username '" + username + "' already exists.");
        }
        User userToUpdate = getUserByUsername(username, authentication);
        if (!isAdmin(authentication) && !userToUpdate.getUsername().equals(authentication.getName())) {
            throw new SecurityException("You can only update your own profile.");
        }

        if (updateRequestDTO.getUsername() != null && !updateRequestDTO.getUsername().isBlank()) {
            userToUpdate.setUsername(updateRequestDTO.getUsername());
        }

        if (updateRequestDTO.getPassword() != null && !updateRequestDTO.getPassword().isBlank()) {
            userToUpdate.setPassword(passwordEncoder.encode(updateRequestDTO.getPassword()));
        }

        return userRepository.save(userToUpdate);
    }

    @Override
    public User updateUserRole(String username, UserUpdateRoleRequestDTO updateRoleRequestDTO, Authentication authentication) {
        if (!isAdmin(authentication)) {
            throw new SecurityException("You can only update your own profile.");
        }
        if (!userRepository.existsByUsername(username)) {
            throw new EntityNotFoundException("User with username '" + username + "' does not exists.");
        }

        User userToUpdate = getUserByUsername(username, authentication);
        userToUpdate.setRole(updateRoleRequestDTO.getRole());

        return userRepository.save(userToUpdate);
    }

    @Override
    public void updateUserSecurityLevel(String username, int securityLevel, Authentication authentication) {
        if (!isAdmin(authentication)) {
            throw new SecurityException("Only admin can update security level.");
        }
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new EntityNotFoundException("User with username '" + username + "' not found.");
        }

        if (securityLevel < 1 || securityLevel > 3) {
            throw new IllegalArgumentException("Invalid security level. Must be between 1 and 3.");
        }

        user.setSecurityLevel(securityLevel);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUserByUsername(String username, Authentication authentication) {
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