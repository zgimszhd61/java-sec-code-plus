package com.freedom.securitysamples.api.openUrl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Unvalidated Redirect
@RestController
@RequestMapping("/api")
public class OpenController {
    @GetMapping("/Open/bad01")
    public String UnvalidatedRedirect(String url){
        return "redirect:" + url;
    }
}
