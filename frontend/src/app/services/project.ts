import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Project {
    id: string;
    title: string;
    description: string;
    techStack: string;
    githubUrl: string;
    liveUrl: string;
    imageUrl: string;
    featured: boolean;
    createdAt: string;
  }

@Injectable({
    providedIn: 'root'
  })


  export class ProjectService{
      private apiUrl = 'http://localhost:8080/api/v1/projects';

      constructor(private http: HttpClient){}

      getAllProjects(): Observable<Project[]>{
        return this.http.get<Project[]>(this.apiUrl);
      }

      getFeaturedProjects(): Observable<Project[]>{
        return this.http.get<Project[]>(`${this.apiUrl}/featured`)};
      }
