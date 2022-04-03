package com.rickihastings.cleanarchitecture.web.configs;

import com.rickihastings.cleanarchitecture.domain.User;
import com.rickihastings.cleanarchitecture.domain.UserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class SessionScopeConfig {

    @Bean
    @SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    public User user() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            var user = auth.getPrincipal();

            if (user instanceof UserDetails) {
                return ((UserDetails) user).getUser();
            }
        }

        return null;
    }
}
