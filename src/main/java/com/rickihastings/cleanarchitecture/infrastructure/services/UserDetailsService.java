package com.rickihastings.cleanarchitecture.infrastructure.services;

import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IUserRepository;
import com.rickihastings.cleanarchitecture.domain.User;
import com.rickihastings.cleanarchitecture.domain.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.getUserByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Could not find user");
        }

        var entity = user.get();
        var authorities = entity
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_USER"))
                .collect(Collectors.toList());

        return new UserDetails(entity, authorities);
    }
}
