package com.rickihastings.cleanarchitecture.application.projects.queries.getproject;

import an.awesome.pipelinr.Command;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rickihastings.cleanarchitecture.application.projects.ProjectDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class GetProjectQuery implements Command<ProjectDto> {
    @JsonProperty("id")
    @NotNull(message = "id cannot be null")
    private Long id;
}
