package com.rickihastings.cleanarchitecture.application.projects.queries.getprojects;

import an.awesome.pipelinr.Command;
import com.rickihastings.cleanarchitecture.application.common.annotations.Authenticate;
import com.rickihastings.cleanarchitecture.application.projects.ProjectDto;
import java.util.List;

@Authenticate
public class GetProjectsQuery implements Command<List<ProjectDto>> {
}
