package com.freedom.javacodesimple.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "hello";
    }
    @GetMapping("/ok")
    public String ok(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "ok";
    }
}
