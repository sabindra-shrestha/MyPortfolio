package com.sabindra.portfolio.service;

import com.sabindra.portfolio.entity.Project;
import jakarta.validation.Valid;



import java.util.List;
import java.util.UUID;

public interface ProjectService {

    List<Project> getAllProjects();

    List<Project> getFeaturedProjects();

    Project createProject(Project project);

    Project getProjectById(UUID id);

    Project updateProject(UUID id,Project project);

    void deleteProject(UUID id);
}
