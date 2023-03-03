package com.shaks.securityapp.config;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String secret_key = "123";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
           try{
               String token = authorizationHeader.substring("Bearer ".length());
               Algorithm algorithm = Algorithm.HMAC256(secret_key.getBytes());
               JWTVerifier  verifier = com.auth0.jwt.JWT.require(algorithm).build();
               DecodedJWT decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

               Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
               Arrays.stream(roles).forEach(role -> {
                   authorities.add(new SimpleGrantedAuthority(role));
               });

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
               SecurityContextHolder.getContext().setAuthentication(authenticationToken);

               filterChain.doFilter(request, response);
           } catch (Exception e) {
               response.setHeader("error", e.getMessage());
               response.setStatus(FORBIDDEN.value());
               Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
           }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
