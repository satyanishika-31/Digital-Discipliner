package com.ip_project.habit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    @PostMapping("/RegisterServlet") // Matches <form action="RegisterServlet">
    @SuppressWarnings({ "CallToPrintStackTrace", "UseSpecificCatch" })
    public String handleRegister(@RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        try {
            // Load the modern MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connection using your exact credentials
            try (Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/habit_db", "root", "nishika0205")) {

                // Change "full_name" to "name" in the INSERT query
                String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, fullName);
                ps.setString(2, email);
                ps.setString(3, password);

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    // Success: Redirect to login page
                    return "redirect:/login.html?signup=success";
                } else {
                    return "redirect:/signin.html?error=failed";
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            // Email already exists in the database
            return "redirect:/signin.html?error=exists";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/signin.html?error=db";
        }
    }
}