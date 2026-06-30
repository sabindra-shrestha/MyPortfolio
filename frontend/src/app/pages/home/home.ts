import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectService, Project } from '../../services/project';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home implements OnInit {
  featuredProjects: Project[] = [];
  loading = true;
  errorMessage = '';

  constructor(private projectService: ProjectService) {}

  ngOnInit(): void {
    this.projectService.getFeaturedProjects().subscribe({
      next: (projects) => {
        this.featuredProjects = projects;
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
