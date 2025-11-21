package com.imfathi.bankingapichallenge.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("/register")
    public String register() {
        return "Registration successful";
    }

    @PostMapping("/login")
    public String login() {
        return "Login successful";
    }

    @PostMapping("/logout")
    public String logout() {
        return "Logout successful";
    }

    @GetMapping("/me")
    public String getUser() {
        return "User details";
    }

}
