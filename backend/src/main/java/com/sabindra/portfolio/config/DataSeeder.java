package com.sabindra.portfolio.config;

import com.sabindra.portfolio.entity.User;
import com.sabindra.portfolio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //Check: does an admin already exits. This guards important-without it, every time you restart the app/
    //it would try to create a duplicate "admin" user and crash.
    @Override
    public void run(String... args){
       //Only create the admin if it doesn't already exist
        if(userRepository.findByUsername("admin").isEmpty()){

            User admin = User.builder()
                    .username("admin")
                    .email("admin@portfolio.com")
                    //this is the one and only moment the plain text password exits in memory. It gets hashed immediately
                    // and only the hash is saved to the database.
                    .passwordHash(passwordEncoder.encode("Admin@123"))
                    .role(User.Role.ADMIN)
                    .build();
            userRepository.save(admin);
            System.out.println("✅ Admin user created: admin / Admin@123");

        }
    }

}
