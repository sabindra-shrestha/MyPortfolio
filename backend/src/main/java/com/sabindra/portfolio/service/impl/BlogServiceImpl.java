package com.sabindra.portfolio.service.impl;

import com.sabindra.portfolio.entity.Blog;
import com.sabindra.portfolio.exception.ResourceNotFoundException;
import com.sabindra.portfolio.repository.BlogRepository;
import com.sabindra.portfolio.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    @Override
    public List<Blog> getPublishedBlogs(){
        return blogRepository.findByPublishedTrue();
    }

    @Override
    public Blog getBlogById(UUID id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog nog found with id: " + id));
    }

    @Override
    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(UUID id, Blog updateBlog) {
        Blog existing = getBlogById(id);
        existing.setTitle(updateBlog.getTitle());
        existing.setContent(updateBlog.getContent());
        existing.setSlug(updateBlog.getSlug());
        existing.setPublished(updateBlog.getPublished());
        return blogRepository.save(existing);
    }

    @Override
    public void deleteBlog(UUID id) {
        Blog existing = getBlogById(id);
        blogRepository.delete(existing);
    }


}
