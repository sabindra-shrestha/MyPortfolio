package com.sabindra.portfolio.service;

import com.sabindra.portfolio.entity.Blog;

import java.util.List;
import java.util.UUID;

public interface BlogService {
    List<Blog> getPublishedBlogs();
    Blog getBlogById(UUID id);
    Blog createBlog(Blog blog);
    Blog updateBlog(UUID id, Blog updateBlog);
    void deleteBlog(UUID id);
}
