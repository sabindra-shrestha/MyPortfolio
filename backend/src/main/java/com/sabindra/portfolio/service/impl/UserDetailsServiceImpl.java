package com.sabindra.portfolio.service.impl;

import com.sabindra.portfolio.entity.User;
import com.sabindra.portfolio.repository.UserRepository;
import com.sabindra.portfolio.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailService {

    //dependency injection @RequiredArgsConstructor
    private final UserRepository userRepository;

    //This contract requires us to fill in.spring security will call this method by itself,
    //automatically, whenever someone tries to log in. We never cal it manually.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = (User) userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPasswordHash(),
                List.of(new SimpleGrantedAuthority("Role_" + user.getRole().name())));
    }


}
