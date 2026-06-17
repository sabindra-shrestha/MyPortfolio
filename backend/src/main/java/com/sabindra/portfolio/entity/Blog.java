package com.sabindra.portfolio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "blogs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(nullable = false)
    private Boolean published = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private  LocalDateTime updatedAt;

    @PrePersist
    protected void OnCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();

    }
}
