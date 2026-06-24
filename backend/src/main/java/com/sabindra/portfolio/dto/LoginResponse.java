package com.sabindra.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


/*
JSON we send back after a successful login.
We deliberately never include the passwords or password hash here--sending back that would be a serious security mistakes.
 */
@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String username;
    private String role;
}
