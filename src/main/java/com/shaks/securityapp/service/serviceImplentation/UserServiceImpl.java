package com.shaks.securityapp.service.serviceImplentation;

import com.shaks.securityapp.entity.Role;
import com.shaks.securityapp.entity.Users;
import com.shaks.securityapp.repository.RoleRepository;
import com.shaks.securityapp.repository.UserRepository;
import com.shaks.securityapp.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Users saveUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addToUser(String username, String roleName) {
        Users user = userRepository.findByEmail(username).get();
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);

    }
}
