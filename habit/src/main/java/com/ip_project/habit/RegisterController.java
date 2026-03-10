package com.ip_project.habit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    private final UserRepository userRepository;

    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/RegisterServlet")
    public String handleRegister(@RequestParam("fullName") String fullName,
                                 @RequestParam("email") String email,
                                 @RequestParam("password") String password) {
        try {
            User newUser = new User();
            newUser.setFullName(fullName);
            newUser.setEmail(email);
            newUser.setPassword(password);

            userRepository.save(newUser);
            return "redirect:/login.html?signup=success";
        } catch (DataIntegrityViolationException e) {
            return "redirect:/signin.html?error=exists";
        } catch (Exception e) {
            logger.error("Error during user registration", e);
            return "redirect:/signin.html?error=db";
        }
    }
}