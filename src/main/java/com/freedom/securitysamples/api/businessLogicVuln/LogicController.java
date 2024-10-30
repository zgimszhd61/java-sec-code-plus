package com.freedom.securitysamples.api.logicVulnerability;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 2023.06.28-潜在逻辑漏洞问题.
@RestController
@RequestMapping("/api")
public class LogicController {
    @GetMapping("/logic/validateUser")
    public String validateUserAccess(String username){
        if (username.equals("ADMIN")) {
            return "{'message':'success'}";
        }
        return  "{'message':'failure'}";
    }
}