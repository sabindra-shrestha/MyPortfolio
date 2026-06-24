package com.sabindra.portfolio.controller;

import com.sabindra.portfolio.entity.Blog;
import com.sabindra.portfolio.service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/blogs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BlogController {

    private final BlogService blogService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping
    public ResponseEntity<List<Blog>> getPublishedBlogs(){
        return ResponseEntity.ok(blogService.getPublishedBlogs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable UUID id){
        return ResponseEntity.ok(blogService.getBlogById(id));
    }

    @PostMapping
    public ResponseEntity<Blog> createBlog(@Valid @RequestBody Blog blog){
        Blog created = blogService.createBlog(blog);

        //created kafka blog-events
        String message = "New Blog Created: " + created.getTitle();
        kafkaTemplate.send("blog-events", message);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable UUID id){
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }
}
