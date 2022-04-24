package com.rickihastings.cleanarchitecture.seeds;

import com.rickihastings.cleanarchitecture.domain.Role;
import com.rickihastings.cleanarchitecture.domain.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserSeeds {

    public static List<User> getUsers() {
        var role = new Role();
        role.setName("USER");

        var user = new User();
        user.setUsername("joebloggs");
        user.setFirstName("Joe");
        user.setLastName("Bloggs");
        user.setRoles(List.of(role));

        return List.of(user);
    }

    public static Optional<User> getUser(String username) {
        var users = getUsers();

        return users
                .stream()
                .filter(u -> Objects.equals(u.getUsername(), username))
                .findFirst();
    }
}
