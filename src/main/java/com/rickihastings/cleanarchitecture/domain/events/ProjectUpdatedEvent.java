package com.rickihastings.cleanarchitecture.domain.events;

import com.rickihastings.cleanarchitecture.domain.common.DomainEvent;
import com.rickihastings.cleanarchitecture.domain.entities.Project;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProjectUpdatedEvent extends DomainEvent {
    public Project item;
}
