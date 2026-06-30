import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectService, Project } from '../../services/project';

@Component({
  selector: 'app-projects',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './projects.html',
  styleUrl: './projects.scss',
})
export class Projects implements OnInit {
  allProjects: Project[] = [];
  loading = true;
  errorMessage = '';

  constructor(private projectService: ProjectService) {}

  ngOnInit(): void {
    this.projectService.getAllProjects().subscribe({
      next: (projects) => {
        this.allProjects = projects;
        this.loading = false;
      },
        error: (err) => {
          console.error('Failed to load projects', err);
          this.errorMessage = 'could not load projects right now';
          this.loading = false;
        }
      });
    }
}
