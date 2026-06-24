package com.sabindra.portfolio.controller;

import com.sabindra.portfolio.config.JwtUtil;
import com.sabindra.portfolio.dto.LoginRequest;
import com.sabindra.portfolio.dto.LoginResponse;
import com.sabindra.portfolio.entity.User;
import com.sabindra.portfolio.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

//    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request){

        //it calls UserDetailsServiceImpl to look up the user, then users BCryptPasswordEncoder to check if the typed
        //password's hash matches the store hash. if anything is wrong, this line throws an exception automatically
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
//        );

        //After the correct password we fetch the full User entity again so we can read their role.
//        User user = (User) userRepository.findByUsername(request.getUsername()).orElseThrow(()-> new RuntimeException("User not Found"));

        User user = userRepository.findByUsername((request.getUsername())).orElse(null);

        if(user == null || !passwordEncoder.matches(request.getPassword(), user.getPasswordHash())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        //Mint a fresh badge and send it back wrapped in our LoginResponse DTO
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        return ResponseEntity.ok(new LoginResponse(token, user.getUsername(), user.getRole().name()));
    }
}
