package com.rickihastings.cleanarchitecture.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String title;

    private boolean archived = false;
}
