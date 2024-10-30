package com.freedom.securitysamples.api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/greet")
    public String getGreeting(@RequestParam(value = "userName", defaultValue = "World") String userName) {
        return "hello, " + userName;
    }

    @GetMapping("/status")
    public String getStatus(@RequestParam(value = "userName", defaultValue = "World") String userName) {
        return "ok, " + userName;
    }
}