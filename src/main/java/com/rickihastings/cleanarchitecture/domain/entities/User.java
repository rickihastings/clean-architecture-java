package com.rickihastings.cleanarchitecture.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String password;

    @Column
    private boolean isActive;

    @ManyToMany(cascade=CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private Collection<Role> roles;

    public boolean getIsActive() {
        return isActive;
    }

    public String getName() {
        return String.format("%s %s", firstName, lastName);
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }
}
