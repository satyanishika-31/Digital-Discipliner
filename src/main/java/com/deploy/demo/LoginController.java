package com.deploy.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession; // Required for session management

@Controller
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // FIX 1: Changed mapping to "/LoginServlet" to match your HTML form action
    @PostMapping("/LoginServlet") 
    public String handleLogin(@RequestParam("email") String email,
                             @RequestParam("password") String password,
                             HttpSession session) { // FIX 2: Added session parameter
        
        return userRepository.findByEmail(email)
            .filter(user -> user.getPassword().equals(password))
            .map(user -> {
                // FIX 3: Store user info in session so the dashboard can display it
                session.setAttribute("userEmail", user.getEmail());
                session.setAttribute("userName", user.getFullName());
                
                // FIX 4: Redirect to "habit.html" instead of "dashboard.html"
                return "redirect:/habit.html"; 
            })
            .orElse("redirect:/login.html?error=invalid");
    }
}