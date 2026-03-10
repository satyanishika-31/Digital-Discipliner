package com.ip_project.habit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam("email") String email,
                             @RequestParam("password") String password) {
        return userRepository.findByEmail(email)
            .filter(user -> user.getPassword().equals(password))
            .map(user -> "redirect:/dashboard.html")
            .orElse("redirect:/login.html?error=invalid");
    }
}