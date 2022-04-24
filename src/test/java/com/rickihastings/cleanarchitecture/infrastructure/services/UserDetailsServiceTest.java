package com.rickihastings.cleanarchitecture.infrastructure.services;

import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IUserRepository;
import com.rickihastings.cleanarchitecture.seeds.UserSeeds;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.when;

public class UserDetailsServiceTest {

    private UserDetailsService userDetailsService;
    private IUserRepository userRepository = Mockito.mock(IUserRepository.class);

    @BeforeEach
    void setup() {
        userDetailsService = new UserDetailsService(userRepository);
    }

    @Test
    public void shouldLoadUserByUsername() {
        var username = "joebloggs";
        when(userRepository.getUserByUsername(username)).thenReturn(UserSeeds.getUser(username));

        var details = userDetailsService.loadUserByUsername(username);

        assertThat(details).isNotNull();
    }

    @Test
    public void shouldThrowUsernameNotFoundException() {
        var username = "unknown";
        when(userRepository.getUserByUsername(username)).thenReturn(Optional.empty());

        assertThrowsExactly(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));
    }
}
