import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BlogService, Blog } from '../../services/blog';

@Component({
  selector: 'app-blog',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './blog.html',
  styleUrl: './blog.scss',
})
export class BlogComponent implements OnInit{
  blogs: Blog[] = [];
  loading = true;
  errorMessage = '';

  constructor(private blogService: BlogService){}

  ngOnInit(): void{
    this.blogService.getPublishedBlogs().subscribe({
      next: (blogs) => {
        this.blogs = blogs;
        this.loading = false;
        },
        error: (err) => {
          console.error('Failed to load blogs', err);
          this.errorMessage = 'could not load blog posts right now.';
          this.loading = false;
        }
    });
  }
}
