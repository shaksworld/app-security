package com.shaks.securityapp.service;

import com.shaks.securityapp.auth.AuthenticationRequest;
import com.shaks.securityapp.auth.AuthenticationResponse;
import com.shaks.securityapp.entity.Role;
import com.shaks.securityapp.entity.Users;
import com.shaks.securityapp.repository.RoleCustomRepo;
import com.shaks.securityapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final RoleCustomRepo roleCustomRepo;
    private AuthenticationManager authenticationManager;

    private JwtService jwtService;

    public AuthenticationResponse authentication(AuthenticationRequest authenticationRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        Users user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
        List<Role> roles = null;

        if(user!=null){
            roles = roleCustomRepo.getRole(user);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Set<Role> set = new HashSet<>();

        roles.stream().forEach(c->set.add(new Role(c.getName())));
        user.setRoles(set);
        set.stream().forEach(i->authorities.add(new SimpleGrantedAuthority(i.getName())));
        var jwtToken = jwtService.generatedToken(user, authorities);
        var jwtFreshToken = jwtService.generatedFreshToken(user, authorities);
        return AuthenticationResponse.builder().token(jwtToken).refreshToken(jwtFreshToken).build();
    }
}
