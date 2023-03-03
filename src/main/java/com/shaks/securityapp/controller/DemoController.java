package com.shaks.securityapp.controller;

import com.shaks.securityapp.auth.AuthenticationRequest;
import com.shaks.securityapp.auth.AuthenticationResponse;
import com.shaks.securityapp.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/demo")
public class DemoController {

    public AuthenticationService authenticationService;

    @PostMapping("/test")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Authentication and Authorization Successful ");
    }

}
