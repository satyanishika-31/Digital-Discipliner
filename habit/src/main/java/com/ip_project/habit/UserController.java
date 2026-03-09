package com.ip_project.habit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import java.util.HashMap;

@RestController
public class UserController {
    @GetMapping("/api/user-info")
    public Map<String, String> getUserInfo(HttpSession session) {
        Map<String, String> info = new HashMap<>();
        info.put("name", (String) session.getAttribute("userName"));
        info.put("email", (String) session.getAttribute("userEmail"));
        return info;
    }
}