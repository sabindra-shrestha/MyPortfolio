package com.sabindra.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/*
A DTO(Data Transfer Object) is a class whose only job is to define the shape of Json going in or out of an API
It's never saved to a database directly
POST to /login: {"username": "admin", "password": "Admin@123"}
 */
@Data
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;


}
