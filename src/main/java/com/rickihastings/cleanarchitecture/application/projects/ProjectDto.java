package com.rickihastings.cleanarchitecture.application.projects;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ProjectDto {
    public Long id;
    public String title;
    public Date createdAt;
    public String createdBy;
}
