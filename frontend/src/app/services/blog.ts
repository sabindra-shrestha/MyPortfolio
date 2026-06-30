import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Blog {
  id: string;
  title: string;
  content: string;
  slug: string;
  published: boolean;
  createdAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class BlogService {
  private apiUrl = 'http://localhost:8080/api/v1/blogs';

  constructor(private http: HttpClient) {}

  getPublishedBlogs(): Observable<Blog[]> {
    return this.http.get<Blog[]>(this.apiUrl);
  }
}
