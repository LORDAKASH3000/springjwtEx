package com.example.springjwtEx.controler;

import com.example.springjwtEx.model.AuthenticationResponse;
import com.example.springjwtEx.model.User;
import com.example.springjwtEx.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request
        ){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request
        ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/demo")
    public ResponseEntity<String> demo(){
        return ResponseEntity.ok("Hello from secured url!");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> admin_only(){
        return ResponseEntity.ok("Hello from secured admin url!");
    }
}
