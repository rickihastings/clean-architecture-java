package com.rickihastings.cleanarchitecture.infrastructure.repositories;

import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IUserRepository;
import com.rickihastings.cleanarchitecture.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserRepository extends IUserRepository, JpaRepository<User, Long> {
     Optional<User> getUserByUsername(String username);
     User save(@NonNull User user);
}
