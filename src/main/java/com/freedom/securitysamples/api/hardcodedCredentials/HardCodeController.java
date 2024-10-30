package com.freedom.securitysamples.api.hardcodedCredentials;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HardCodeController {
    @GetMapping("/hardPassword/bad01")
    public String retrieveHardCodedPassword(){
        String secretPassword = "letMeIn!";
        String apiAccessKeyId = "letMeIn!";
        String apiAccessKeySecret = "letMeIn!";
        return  "{'msg':'success'}";
    }
}