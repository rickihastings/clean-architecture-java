package com.rickihastings.cleanarchitecture.utils;

import au.com.origin.snapshots.serializers.DeterministicJacksonSnapshotSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rickihastings.cleanarchitecture.application.projects.ProjectDto;

import java.time.Instant;

public class DefaultSnapshotSerializer extends DeterministicJacksonSnapshotSerializer {

    @Override
    public void configure(ObjectMapper objectMapper) {
        super.configure(objectMapper);

        objectMapper.addMixIn(ProjectDto.class, IgnoreEntityFields.class);
    }

    abstract static class IgnoreEntityFields {
        @JsonIgnore
        abstract Instant getCreatedAt();

        @JsonIgnore
        abstract Instant getUpdatedAt();
    }
}
