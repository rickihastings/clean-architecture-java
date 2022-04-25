package com.rickihastings.cleanarchitecture.application.projects.commands.deleteproject;

import an.awesome.pipelinr.Command;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rickihastings.cleanarchitecture.application.common.annotations.Authenticate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Authenticate
public class DeleteProjectCommand implements Command<DeleteProjectDto> {
    @JsonProperty("id")
    @NotNull(message = "id cannot be null")
    private Long id;
}
