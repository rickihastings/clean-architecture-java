package com.rickihastings.cleanarchitecture.seeds;

import com.rickihastings.cleanarchitecture.domain.entities.Role;
import com.rickihastings.cleanarchitecture.domain.entities.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
                .filter(user -> Objects.equals(user.getUsername(), username))
                .findFirst();
    }
}
