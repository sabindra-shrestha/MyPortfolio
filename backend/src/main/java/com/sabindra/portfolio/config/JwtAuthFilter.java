package com.sabindra.portfolio.config;

import com.sabindra.portfolio.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class
JwtAuthFilter extends OncePerRequestFilter {

    //the badge-reader(JwtUtil) and database-translator(UserDetailsServiceImpl)
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    //This is Spring base class specifically designed to run exactly once per incoming HTTP request.
    // (Without this, in some server setups a filter could accidentally run twice for the same request.)
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //pull out the authorization header from the request, if any was sent. This is where the browser put the token.
        final String authHeader = request.getHeader("Authorization");

        //if there's no header at all, or lit doesn't start with the word "Bearer" - there is nothing to check.
        // We just let the request continue and exit this method early.
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        //(B-e-a-r-e-r-space) chops off the first 7 characters, leaving just the raw token
        final String jwt = authHeader.substring(7);

        //user our badge-reader from File 1 to find out whose badge this claims to be.
        final String username = jwtUtil.extractUsername(jwt);

        //Two checks: did we actually get a username back, And has nobody already authenticated this request yet.
        //(avoids redundant work if some other filter already did this)
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //look this username up in the real database(this calls the method we wrote in File 2)
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Double-check; is the token's signature valid, not expired and does it actually match this username
            if (jwtUtil.validateToken(jwt, username)){

                //Actual stamp of approval -- we build authToken objects -- Spring security's internal representation
                //of this request to this authenticated user with these permissions.
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //Store in SecurityContextHolder which is basically per-request shelf Spring security checks whenever
                //it needs to know who is making this request right now.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        //we let the request continue down the chain toward your controller.-- we just optionally attached an "ID badge": to it first.
        filterChain.doFilter(request, response);

    }
}
