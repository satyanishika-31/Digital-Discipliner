package com.ip_project.habit;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
public class habit {
    @RequestMapping("/habit")
    public String  hello(){
        return "hello";
    }
    
}
