package com.ip_project.habit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/RegisterServlet") // Matches <form action="RegisterServlet">
    public String handleRegister(@RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        try {
            // Create new user using JPA
            User newUser = new User();
            newUser.setFullName(fullName);
            newUser.setEmail(email);
            newUser.setPassword(password);
            
            userRepository.save(newUser);
            
            // Success: Redirect to login page
            return "redirect:/login.html?signup=success";
            
        } catch (DataIntegrityViolationException e) {
            // Email already exists in the database
            return "redirect:/signin.html?error=exists";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/signin.html?error=db";
        }
    }
}