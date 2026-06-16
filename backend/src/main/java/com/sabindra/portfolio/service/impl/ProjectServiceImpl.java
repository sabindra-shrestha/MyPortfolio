package com.sabindra.portfolio.service.impl;

import com.sabindra.portfolio.entity.Project;
import com.sabindra.portfolio.repository.ProjectRepository;
import com.sabindra.portfolio.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;


    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> getFeaturedProjects() {
        return projectRepository.findByFeaturedTrue();
    }

    @Override
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project getProjectById(UUID id) {
        return projectRepository.getById(id);
    }

    @Override
    public Project updateProject(UUID id, Project project) {
        Project existing = getProjectById(id);
        existing.setTitle(project.getTitle());
        existing.setDescription(project.getDescription());
        existing.setTechStack(project.getTechStack());
        existing.setGithubUrl(project.getGithubUrl());
        existing.setLiveUrl(project.getLiveUrl());
        existing.setImageUrl(project.getLiveUrl());
        existing.setFeatured(project.getFeatured());

        return projectRepository.save(existing);
    }

    @Override
    public void deleteProject(UUID id) {
        Project existing = getProjectById(id);
        projectRepository.delete(existing);
    }


}
