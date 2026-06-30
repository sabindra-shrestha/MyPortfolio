import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-about',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './about.html',
  styleUrl: './about.scss',
})
export class About {
  skills = [
    { category: 'Backend', items: ['Java', 'Spring Boot', 'PostgresSQL']},
    { category: 'Frontend', items: ['Angular', 'TypeScript', 'RxJS']},
    { category: 'DevOps', items: ['Docker', 'GitHub Actions', 'AWS']}
    ];
  }
