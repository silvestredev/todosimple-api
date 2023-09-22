package dev.silvestredev.todosimple.services;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.silvestredev.todosimple.repositories.UserRepository;
import dev.silvestredev.todosimple.security.UserSpringSecurity;
import dev.silvestredev.todosimple.services.exceptions.ObjectNotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        var user = userRepository.findByName(username);

        if (Objects.isNull(user)) {
            throw new ObjectNotFoundException("Usuário não encontrado: " + username);
        }

        return new UserSpringSecurity(user.getId(), user.getName(), user.getPassword(), user.getProfiles());
    }
}
