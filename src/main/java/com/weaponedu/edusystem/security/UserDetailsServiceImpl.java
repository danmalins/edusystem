package com.weaponedu.edusystem.security;

import com.weaponedu.edusystem.model.User;
import com.weaponedu.edusystem.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service // Объявляем класс как Spring Service (компонент)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    // Инъекция зависимости UserRepository через конструктор
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Основной метод, требуемый интерфейсом UserDetailsService.
     * Загружает объект пользователя по его логину (username).
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(username == null ||  username.isBlank())
            throw new UsernameNotFoundException("User not found");
        return new MyUserDetails(user);
    }
}