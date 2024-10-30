package com.freedom.securitysamples.api.jsonpCallback;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class JsonpController {


    //2023.06.25:返回值为success.
    @GetMapping("/jsonp/bad01")
    public String jsonp01(String callback){
        return callback + "{'msg':'success'}";
    }
}
