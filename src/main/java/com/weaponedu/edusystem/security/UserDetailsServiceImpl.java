package com.weaponedu.edusystem.security;

import com.weaponedu.edusystem.model.User;
import com.weaponedu.edusystem.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if(email == null ||  email.isBlank())
            throw new UsernameNotFoundException("User not found");
        User user = userRepository.findByEmail(email);
        return new MyUserDetails(user);
    }
}