package com.freedom.securitysamples.api.redos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ReDoSController {
    @GetMapping("/redos/bad01")
    public boolean redos01(String regularExpression, String userInput) throws IOException {

        // BAD: Unsanitized user input is used to construct a regular expression
        return userInput.matches(regularExpression);
    }
}