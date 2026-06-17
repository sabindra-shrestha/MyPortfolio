package com.sabindra.portfolio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String username;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String passwordHash;               // NEVER store plain text passwords

    @Enumerated(EnumType.STRING)               // stores "ADMIN" or "VISITOR" as text, not 0/1
    @Column(nullable = false)
    private Role role = Role.VISITOR;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum Role {
        ADMIN, VISITOR
    }
}