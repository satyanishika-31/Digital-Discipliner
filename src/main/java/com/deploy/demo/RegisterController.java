package com.deploy.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    private final UserRepository userRepository;

    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @PostMapping("/RegisterServlet")
public String handleRegister(@RequestParam("fullName") String fullName,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password,
                             jakarta.servlet.http.HttpSession session) { // Add session here
    try {
        User newUser = new User();
        newUser.setFullName(fullName);
        newUser.setEmail(email);
        newUser.setPassword(password);

        userRepository.save(newUser);
        
        // Auto-login after registration
        session.setAttribute("userEmail", email);
        session.setAttribute("userName", fullName);
        
        return "redirect:/habit.html";
    } catch (Exception e) {
        return "redirect:/signin.html?error=db";
    }
}
    
}