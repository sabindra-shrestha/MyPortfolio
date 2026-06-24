package com.sabindra.portfolio.controller;


import com.sabindra.portfolio.entity.Project;
import com.sabindra.portfolio.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class ProjectController {


    private final ProjectService projectService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects(){
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable UUID id){
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @GetMapping("/feature")
    public ResponseEntity<List<Project>> getFeaturedProjects(){
        return ResponseEntity.ok(projectService.getFeaturedProjects());
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project){
        Project created = projectService.createProject(project);

        //created kafka project-event
        String messsage = "New project added: " + created.getTitle();
        kafkaTemplate.send("project-events", messsage);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable UUID id, @Valid @RequestBody Project project){
        return ResponseEntity.ok(projectService.updateProject(id, project));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteProject(@PathVariable UUID id){
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

}
