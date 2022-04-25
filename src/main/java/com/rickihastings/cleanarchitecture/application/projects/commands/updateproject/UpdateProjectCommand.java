package com.rickihastings.cleanarchitecture.application.projects.commands.updateproject;

import an.awesome.pipelinr.Command;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rickihastings.cleanarchitecture.application.common.annotations.Authenticate;
import com.rickihastings.cleanarchitecture.application.projects.ProjectDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Authenticate
public class UpdateProjectCommand implements Command<ProjectDto> {
    @JsonIgnore
    private Long id;

    @JsonProperty("title")
    @NotNull(message = "title cannot be null")
    @NotBlank(message = "title cannot be blank")
    private String title;
}
