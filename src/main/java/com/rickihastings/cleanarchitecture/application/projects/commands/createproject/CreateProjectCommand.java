package com.rickihastings.cleanarchitecture.application.projects.commands.createproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rickihastings.cleanarchitecture.application.projects.ProjectDto;
import io.jkratz.mediator.core.Request;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter @NoArgsConstructor
public class CreateProjectCommand implements Request<ProjectDto> {
    @JsonProperty("title")
    @NotNull(message = "Title cannot be null")
    @NotBlank(message = "Title cannot be blank")
    private String title;
}
