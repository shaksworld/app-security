package com.shaks.securityapp.service;

import com.shaks.securityapp.entity.Role;
import com.shaks.securityapp.entity.Users;

public interface UserService {

    Users saveUser(Users user);

    Role saveRole(Role role);

    void addToUser(String username, String roleName);
}
