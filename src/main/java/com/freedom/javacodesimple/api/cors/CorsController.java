package com.freedom.javacodesimple.api.cors;

import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CorsController {

    @GetMapping("/cors/example01")
    public void configureCorsHeaders(ServerHttpResponse response){
        response.getHeaders().add("Access-Control-Allow-Origin", "*");
    }
}
