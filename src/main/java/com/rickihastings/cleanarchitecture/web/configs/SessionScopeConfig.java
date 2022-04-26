package com.rickihastings.cleanarchitecture.web.configs;

import com.rickihastings.cleanarchitecture.domain.entities.User;
import com.rickihastings.cleanarchitecture.domain.entities.UserDetails;
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
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null &&
                authentication
                        .getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))
        ) {
            var user = authentication.getPrincipal();

            if (user instanceof UserDetails) {
                return ((UserDetails) user).getUser();
            }
        }

        return null;
    }
}
